ALTER TABLE minting_event
    CHANGE name title VARCHAR(255) COLLATE utf8mb4_bin NOT NULL;

ALTER TABLE minting_event
    ADD COLUMN description VARCHAR(255) COLLATE utf8mb4_bin NOT NULL AFTER title,
    ADD COLUMN contents    TEXT COLLATE utf8mb4_bin         NOT NULL AFTER description,
    ADD COLUMN resource    VARCHAR(255) COLLATE utf8mb4_bin NOT NULL AFTER contents;
