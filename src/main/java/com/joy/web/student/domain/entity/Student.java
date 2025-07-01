package com.joy.web.student.domain.entity;

import com.joy.config.util.DataKeyGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  @Builder.Default
  private final String uuid = DataKeyGenerator.generateUniqueId();

  private String name;

  private String description;

  private String contents;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  @Column(nullable = false)
  @CreatedDate
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @LastModifiedDate
  private LocalDateTime modifiedDate;

  @Override
  public final boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    final Class<?> objEffectiveClass = obj instanceof HibernateProxy ? ((HibernateProxy) obj).getHibernateLazyInitializer().getPersistentClass() : obj.getClass();
    final Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (thisEffectiveClass != objEffectiveClass) {
      return false;
    }
    final Student that = (Student) obj;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}
