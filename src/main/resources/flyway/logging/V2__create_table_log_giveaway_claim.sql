CREATE TABLE log_giveaway_claim
(
    id             BIGINT AUTO_INCREMENT            NOT NULL,
    sui_address    VARCHAR(50) COLLATE utf8mb4_bin  NOT NULL,
    ip_address     VARCHAR(20) COLLATE utf8mb4_bin  NOT NULL,
    os             VARCHAR(100) COLLATE utf8mb4_bin NOT NULL,
    browser        VARCHAR(100) COLLATE utf8mb4_bin NOT NULL,
    status         INTEGER                          NOT NULL,
    error_message  VARCHAR(100)                     NULL,
    reward         INTEGER                          NOT NULL,
    created_date   datetime                         NOT NULL,
    CONSTRAINT pk_loggiveawayclaim PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;

CREATE INDEX idx_logGiveawayClaim_suiAddress ON log_giveaway_claim (sui_address);
