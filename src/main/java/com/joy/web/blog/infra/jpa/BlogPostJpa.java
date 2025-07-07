package com.joy.web.blog.infra.jpa;

import com.joy.web.blog.domain.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostJpa extends JpaRepository<BlogPost, Long> {

  BlogPost findByUuid(final String uuid);

  void deleteByUuid(final String uuid);
}
