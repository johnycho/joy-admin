CREATE TABLE users
(
    username VARCHAR(50) COLLATE utf8mb4_bin  NOT NULL PRIMARY KEY,
    password VARCHAR(500) COLLATE utf8mb4_bin NOT NULL,
    enabled  BIT(1)                           NOT NULL
);
CREATE TABLE authorities
(
    username  VARCHAR(50) COLLATE utf8mb4_bin NOT NULL,
    authority VARCHAR(50) COLLATE utf8mb4_bin NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);
CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);

INSERT IGNORE INTO users
VALUES ('admin', '$2a$10$9RcW/omM9nMfFTRSTZGH6ujl7kk.nXwp4OlD73gM2JtfGeW8HlZBq', '1');
#password: 'joy250321!@#'
INSERT IGNORE INTO authorities
VALUES ('admin', 'ROLE_ADMIN');

CREATE TABLE student
(
    id            BIGINT AUTO_INCREMENT            NOT NULL,
    uuid          VARCHAR(255) COLLATE utf8mb4_bin NOT NULL,
    name          VARCHAR(255) COLLATE utf8mb4_bin NOT NULL,
    description   VARCHAR(255) COLLATE utf8mb4_bin NULL,
    contents      TEXT COLLATE utf8mb4_bin         NULL,
    start_date    datetime                         NULL,
    end_date      datetime                         NULL,
    created_date  datetime                         NOT NULL,
    modified_date datetime                         NOT NULL,
    CONSTRAINT pk_students PRIMARY KEY (id),
    CONSTRAINT uk_students_uuid UNIQUE (uuid)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;
