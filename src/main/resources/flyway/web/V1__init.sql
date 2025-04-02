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
VALUES ('admin', '$2a$10$P.s0uEEnWJTufrcz49BTb.xpcawK/NXehm7Y/lZdB64hsim6yNJ.G', '1');
#password: 'pebblejackpot123!@#'
INSERT IGNORE INTO authorities
VALUES ('admin', 'ROLE_ADMIN');

CREATE TABLE minting_event
(
    id            BIGINT AUTO_INCREMENT            NOT NULL,
    uuid          VARCHAR(255) COLLATE utf8mb4_bin NOT NULL,
    name          VARCHAR(255) COLLATE utf8mb4_bin NOT NULL,
    start_date    datetime                         NOT NULL,
    end_date      datetime                         NOT NULL,
    created_date  datetime                         NOT NULL,
    modified_date datetime                         NOT NULL,
    CONSTRAINT pk_mintingevent PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;

ALTER TABLE minting_event
    ADD CONSTRAINT uc_mintingevent_uuid UNIQUE (uuid);

CREATE TABLE minting_subject
(
    id                 BIGINT AUTO_INCREMENT            NOT NULL,
    minting_event_uuid VARCHAR(255) COLLATE utf8mb4_bin NOT NULL,
    token_id           BIGINT                           NOT NULL,
    address            VARCHAR(255) COLLATE utf8mb4_bin NOT NULL,
    created_date       datetime                         NOT NULL,
    modified_date      datetime                         NOT NULL,
    CONSTRAINT pk_mintingsubject PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;

ALTER TABLE minting_subject
    ADD CONSTRAINT uc_mintingsubject_tokenid UNIQUE (token_id);

CREATE UNIQUE INDEX uc_mintingeventuuid_address ON minting_subject (minting_event_uuid, address);

ALTER TABLE minting_subject
    ADD CONSTRAINT FK_MINTINGSUBJECT_ON_MINTING_EVENT_UUID FOREIGN KEY (minting_event_uuid) REFERENCES minting_event (uuid);

CREATE TABLE token_identifier
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    token_sequence   BIGINT                NOT NULL,
    start            BIGINT                NOT NULL,
    end              BIGINT                NOT NULL,
    minting_event_id BIGINT                NOT NULL,
    CONSTRAINT pk_tokenidentifier PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;

ALTER TABLE token_identifier
    ADD CONSTRAINT uc_tokenidentifier_mintingeventid UNIQUE (minting_event_id);
