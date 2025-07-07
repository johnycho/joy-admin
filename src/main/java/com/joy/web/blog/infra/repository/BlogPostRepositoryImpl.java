package com.joy.web.blog.infra.repository;

import com.joy.web.blog.domain.entity.BlogPost;
import com.joy.web.blog.domain.repository.BlogPostRepository;
import com.joy.web.blog.infra.jpa.BlogPostJpa;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BlogPostRepositoryImpl implements BlogPostRepository {

  private final BlogPostJpa jpa;

  @Override
  public BlogPost save(final BlogPost student) {
    return jpa.save(student);
  }

  @Override
  public BlogPost findByUuid(String uuid) {
    return jpa.findByUuid(uuid);
  }

  @Override
  public List<BlogPost> findAll() {
    return jpa.findAll();
  }

  @Override
  public void deleteByUuid(final String uuid) {
    jpa.deleteByUuid(uuid);
  }
}
