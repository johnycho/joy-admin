CREATE TABLE IF NOT EXISTS blog_post
(
    id            BIGINT AUTO_INCREMENT            NOT NULL,
    uuid          VARCHAR(255) COLLATE utf8mb4_bin NOT NULL,
    slug          VARCHAR(255) COLLATE utf8mb4_bin NOT NULL,
    title         VARCHAR(255) COLLATE utf8mb4_bin NULL,
    authors       VARCHAR(255) COLLATE utf8mb4_bin NULL,
    tags          VARCHAR(255) COLLATE utf8mb4_bin NULL,
    contents      LONGTEXT     COLLATE utf8mb4_bin NULL,
    created_at    datetime                         NOT NULL,
    modified_at   datetime                         NOT NULL,
    CONSTRAINT pk_blog_post PRIMARY KEY (id),
    CONSTRAINT uk_blog_post_uuid UNIQUE (uuid),
    CONSTRAINT uk_blog_post_slug UNIQUE (slug)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;
