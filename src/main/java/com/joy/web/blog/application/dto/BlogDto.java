package com.joy.web.blog.application.dto;

import com.joy.web.blog.domain.entity.BlogPost;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class BlogDto {

  public static final BlogPostMvcRequest EMPTY_BLOG_POST_REQUEST = new BlogPostMvcRequest(null, null, null, null, null);

  public record BlogPostMvcRequest(@NotBlank(message = "slug를 입력하세요.")
                                   String slug,
                                   @NotBlank(message = "title을 입력하세요.")
                                   String title,
                                   @NotBlank(message = "authors을 입력하세요.")
                                   String authors,
                                   @NotBlank(message = "tags을 입력하세요.")
                                   String tags,
                                   @NotBlank(message = "contents을 입력하세요.")
                                   String contents) {
  }

  public record BlogPostMvcResponse(String uuid,
                                    String slug,
                                    String title,
                                    String authors,
                                    String tags,
                                    String contents,
                                    LocalDateTime createdAt,
                                    LocalDateTime modifiedAt) {

    public static BlogPostMvcResponse of(final BlogPost blogPost) {
      return new BlogPostMvcResponse(blogPost.getUuid(),
                                     blogPost.getSlug(),
                                     blogPost.getTitle(),
                                     blogPost.getAuthors(),
                                     blogPost.getTags(),
                                     blogPost.getContents(),
                                     blogPost.getCreatedAt(),
                                     blogPost.getModifiedAt());
    }
  }
}
