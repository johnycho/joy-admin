package com.joy.web.blog.domain.repository;

import com.joy.web.blog.domain.entity.BlogPost;
import java.util.List;

public interface BlogPostRepository {
  void save(final BlogPost blogPost);

  List<BlogPost> findAll();

  void deleteByUuid(final String uuid);
}
