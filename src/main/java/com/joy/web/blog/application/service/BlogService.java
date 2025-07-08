package com.joy.web.blog.application.service;

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
import java.util.NoSuchElementException;
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

  private static final String RELATIVE_PATH_FORMAT = "blog/{}.md";
  private static final String FRONT_MATTER_FORMAT = """
      ---
      slug: %s
      title: "%s"
      authors: [ %s ]
      tags: [ %s ]
      ---
      
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
    deployBlogPost(resolveFileName(entity), createMarkdown(request));
  }

  @Transactional
  public void delete(final String uuid) {
    final BlogPost entity = Optional.ofNullable(repository.findByUuid(uuid))
                                    .orElseThrow(() -> new NoSuchElementException("No such blog post: " + uuid));
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

  public void deployBlogPost(final String fileName, final String markdownContent) {
    final String relativePath = StringUtil.make(RELATIVE_PATH_FORMAT, fileName);
    final File targetFile = new File(gitRepoPath, relativePath);

    try {
      // .md 파일 생성
      Files.writeString(targetFile.toPath(), markdownContent);

      try (Git git = Git.open(new File(gitRepoPath))) {
        // fetch + pull (upstream 동기화)
        git.fetch()
           .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUsername, gitToken))
           .call();
        git.pull()
           .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUsername, gitToken))
           .call();

        // add + commit + push
        git.add().addFilepattern(relativePath).call();
        git.commit().setMessage("Add new blog post: " + fileName).call();
        git.push()
           .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUsername, gitToken))
           .call();
      }

    } catch (IOException | GitAPIException e) {
      log.error("Failed to deploy markdown file: {}", fileName, e);
    }
  }

  public void deleteBlogPost(final String fileName) {
    final String relativePath = StringUtil.make(RELATIVE_PATH_FORMAT, fileName);
    final File targetFile = new File(gitRepoPath, relativePath);

    if (!targetFile.exists()) {
      log.warn("Markdown file not found: {}", targetFile.getAbsolutePath());
      return;
    }

    try {
      Files.deleteIfExists(targetFile.toPath());

      try (Git git = Git.open(new File(gitRepoPath))) {
        // fetch + pull (upstream 동기화)
        git.fetch()
           .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUsername, gitToken))
           .call();
        git.pull()
           .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUsername, gitToken))
           .call();

        // remove + commit + push
        git.rm().addFilepattern(relativePath).call();
        git.commit().setMessage("Delete blog post: " + fileName).call();
        git.push()
           .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUsername, gitToken))
           .call();
      }

    } catch (IOException | GitAPIException e) {
      log.error("Failed to delete markdown file: {}", fileName, e);
    }
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
                                         request.tags())
           + request.contents();
  }
}
