package com.joy.web.blog.domain.repository;

import com.joy.web.blog.domain.entity.BlogPost;
import java.util.List;

public interface BlogPostRepository {
  BlogPost save(final BlogPost blogPost);

  BlogPost findByUuid(final String uuid);

  List<BlogPost> findAll();

  void deleteByUuid(final String uuid);
}
