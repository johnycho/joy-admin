package com.joy.web.blog.application.service;

import com.joy.web.blog.application.dto.BlogDto.BlogPostMvcRequest;
import com.joy.web.blog.application.dto.BlogDto.BlogPostMvcResponse;
import com.joy.web.blog.domain.repository.BlogPostRepository;
import com.joy.web.blog.presentation.mapper.BlogPostEntityMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

  private static final String FRONT_MATTER_FORMAT = """
      ---
      slug: %s
      title: %s
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
    repository.save(blogPostEntityMapper.toEntity(request));
    deployBlogPost(resolveFileName(request), createMarkdown(request));
  }

  @Transactional
  public void delete(final String uuid) {
    repository.deleteByUuid(uuid);
  }

  @Transactional(readOnly = true)
  public List<BlogPostMvcResponse> findAll() {
    return repository.findAll()
                     .stream()
                     .map(BlogPostMvcResponse::of)
                     .toList();
  }

  public void deployBlogPost(final String fileName, final String markdownContent) {
    final String filePath = gitRepoPath + "/blog/" + fileName + ".md";

    try {
      // .md 파일 생성
      Files.writeString(Paths.get(filePath), markdownContent);

      try (Git git = Git.open(new File(gitRepoPath))) {
        // fetch + pull (upstream 동기화)
        git.fetch()
           .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUsername, gitToken))
           .call();
        git.pull()
           .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUsername, gitToken))
           .call();

        // add + commit + push
        git.add().addFilepattern("blog/" + fileName + ".md").call();
        git.commit().setMessage("Add new blog post: " + fileName).call();
        git.push()
           .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUsername, gitToken))
           .call();
      }

    } catch (IOException | GitAPIException e) {
      log.error("Failed to deploy markdown file: {}", fileName, e);
    }
  }

  private static String resolveFileName(final BlogPostMvcRequest request) {
    return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
           + "-"
           + request.slug();
  }

  private static String createMarkdown(final BlogPostMvcRequest request) {
    return FRONT_MATTER_FORMAT.formatted(request.slug(),
                                         request.title(),
                                         request.authors(),
                                         request.tags())
           + request.contents();
  }
}
