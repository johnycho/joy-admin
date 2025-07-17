package com.joy.web.blog.application.service;

import com.joy.config.exception.constant.ExceptionInfo;
import com.joy.config.util.StringUtil;
import com.joy.web.blog.application.dto.BlogDto.BlogPostMvcRequest;
import com.joy.web.blog.application.dto.BlogDto.BlogPostMvcResponse;
import com.joy.web.blog.domain.entity.BlogPost;
import com.joy.web.blog.domain.repository.BlogPostRepository;
import com.joy.web.blog.presentation.mapper.BlogPostEntityMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogService {

  private static final String RELATIVE_PATH_FORMAT = "blog/{}.mdx";
  private static final String FRONT_MATTER_FORMAT = """
      ---
      slug: %s
      title: "%s"
      authors: [ %s ]
      tags: [ %s ]
      description: "%s"
      keywords:
        - 원주 언어발달센터
        - 원주 기업도시 언어발달센터
        - 원주 지정면 언어발달센터
        - 원주 언어치료
        - 원주 기업도시 언어치료
        - 원주 지정면 언어치료
      ---
      
      """;
  private static final String CONTENTS_PREFIX = """
      <!-- truncate -->
      <br /><br />
      
      """;
  private static final String CONTENTS_POSTFIX = """
      
      <br /><br />
      
      ---
      
      아이들의 언어발달에 즐거움을 더하는 공간 \s
      **조이 언어발달센터**입니다.
      
      ---
      
      Let's enJOY in JOY ! 🎉✨
      
      ---
      
      📍 위치: 원주시 지정면 무릉로 15, JD 스퀘어 6층 (기업도시 토이 아울렛 건물) \s
      📞 문의: 033-745-1030 \s
      💬 카카오채널: [joylangcenter](https://pf.kakao.com/_sxjPXn) \s
      ⭐️ 인스타그램: [@joylangcenter](https://instagram.com/joylangcenter) \s
      🏠 홈페이지: [joylangcenter.com](https://joylangcenter.com)
      ![](../static/img/joy-content-logo.jpg)
      """;

  private final BlogPostEntityMapper blogPostEntityMapper;
  private final BlogPostRepository repository;

  @Value("${git.repo-path}")
  private String gitRepoPath;

  @Value("${git.username}")
  private String gitUsername;

  @Value("${git.token}")
  private String gitToken;

  @Transactional
  public void register(final BlogPostMvcRequest request) {
    final BlogPost entity = repository.save(blogPostEntityMapper.toEntity(request));
    deployNewBlogPost(resolveFileName(entity), createMarkdown(request));
  }

  @Transactional
  public void update(final BlogPostMvcRequest request) {
    final BlogPost entity = Optional.ofNullable(repository.findByUuid(request.uuid()))
                                    .orElseThrow(() -> ExceptionInfo.NOT_FOUND_BLOG_POST.builder()
                                                                                        .debugs(() -> Map.of("uuid", request.uuid()))
                                                                                        .build());
    entity.update(blogPostEntityMapper.toEntity(request));
    deployModifiedBlogPost(resolveFileName(entity), createMarkdown(request));
  }

  @Transactional
  public void delete(final String uuid) {
    final BlogPost entity = Optional.ofNullable(repository.findByUuid(uuid))
                                    .orElseThrow(() -> ExceptionInfo.NOT_FOUND_BLOG_POST.builder()
                                                                                        .debugs(() -> Map.of("uuid", uuid))
                                                                                        .build());
    repository.deleteByUuid(uuid);
    deleteBlogPost(resolveFileName(entity));
  }

  @Transactional(readOnly = true)
  public List<BlogPostMvcResponse> findAll() {
    return repository.findAll()
                     .stream()
                     .map(BlogPostMvcResponse::of)
                     .toList();
  }

  @Transactional(readOnly = true)
  public BlogPostMvcResponse findBy(final String uuid) {
    return Optional.ofNullable(repository.findByUuid(uuid))
                   .map(BlogPostMvcResponse::of)
                   .orElseThrow(() -> ExceptionInfo.NOT_FOUND_BLOG_POST.builder()
                                                                       .debugs(() -> Map.of("uuid", uuid))
                                                                       .build());
  }

  private static String resolveFileName(final BlogPost entity) {
    return entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
           + "-"
           + entity.getSlug();
  }

  private static String createMarkdown(final BlogPostMvcRequest request) {
    return FRONT_MATTER_FORMAT.formatted(request.slug(),
                                         request.title(),
                                         request.authors(),
                                         request.tags(),
                                         request.title())
           + CONTENTS_PREFIX
           + request.contents()
           + CONTENTS_POSTFIX;
  }

  private void deployNewBlogPost(final String fileName, final String markdownContent) {
    deployBlogPost(fileName, markdownContent, false);
  }

  private void deployModifiedBlogPost(final String fileName, final String markdownContent) {
    deployBlogPost(fileName, markdownContent, true);
  }

  private void deployBlogPost(final String fileName, final String markdownContent, boolean isUpdate) {
    final String relativePath = StringUtil.make(RELATIVE_PATH_FORMAT, fileName);
    final File targetFile = new File(gitRepoPath, relativePath);

    try {
      Files.writeString(targetFile.toPath(), markdownContent); // 덮어쓰기 or 생성
      try (final Git git = Git.open(new File(gitRepoPath))) {
        gitFetchAndPull(git);
        git.add().addFilepattern(relativePath).call();
        final String commitMessage = (isUpdate ? "Update blog post: " : "Add new blog post: ");
        gitCommitAndPush(git, commitMessage, fileName);
      }
    } catch (IOException | GitAPIException e) {
      log.error("Failed to deploy markdown file: {}", fileName, e);
    }
  }

  private void deleteBlogPost(final String fileName) {
    final String relativePath = StringUtil.make(RELATIVE_PATH_FORMAT, fileName);
    final File targetFile = new File(gitRepoPath, relativePath);

    if (!targetFile.exists()) {
      log.warn("Markdown file not found: {}", targetFile.getAbsolutePath());
      return;
    }

    try {
      Files.deleteIfExists(targetFile.toPath());
      try (Git git = Git.open(new File(gitRepoPath))) {
        gitFetchAndPull(git);
        git.rm().addFilepattern(relativePath).call();
        gitCommitAndPush(git, "Delete blog post: ", fileName);
      }
    } catch (IOException | GitAPIException e) {
      log.error("Failed to delete markdown file: {}", fileName, e);
    }
  }

  private void gitCommitAndPush(final Git git, final String commitMessage, final String fileName) throws GitAPIException {
    git.commit().setMessage(commitMessage + fileName).call();
    git.push()
       .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUsername, gitToken))
       .call();
  }

  private void gitFetchAndPull(final Git git) throws GitAPIException {
    // fetch + pull (upstream 동기화)
    git.fetch()
       .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUsername, gitToken))
       .call();
    git.pull()
       .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUsername, gitToken))
       .call();
  }
}
