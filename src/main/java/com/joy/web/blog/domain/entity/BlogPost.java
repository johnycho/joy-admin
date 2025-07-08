package com.joy.web.blog.domain.entity;

import com.joy.config.util.DataKeyGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BlogPost {

  @Column(nullable = false, unique = true)
  @Builder.Default
  private final String uuid = DataKeyGenerator.generateUniqueId();
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String slug;

  private String title;

  private String authors;

  private String tags;

  private String contents;

  @Column(nullable = false)
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(nullable = false)
  @LastModifiedDate
  private LocalDateTime modifiedAt;

  public void update(BlogPost blogPost) {
    this.slug = blogPost.getSlug();
    this.title = blogPost.getTitle();
    this.authors = blogPost.getAuthors();
    this.tags = blogPost.getTags();
    this.contents = blogPost.getContents();
  }
}
