CREATE TABLE log_wallet_minting
(
    id             BIGINT AUTO_INCREMENT            NOT NULL,
    wallet_address VARCHAR(50) COLLATE utf8mb4_bin  NOT NULL,
    ip_address     VARCHAR(20) COLLATE utf8mb4_bin  NOT NULL,
    os             VARCHAR(100) COLLATE utf8mb4_bin NOT NULL,
    browser        VARCHAR(100) COLLATE utf8mb4_bin NOT NULL,
    status         INT                              NOT NULL,
    error_message  VARCHAR(100)                     NULL,
    token_id       BIGINT                           NULL,
    event_uuid     VARCHAR(30)                      NOT NULL,
    created_date   datetime                         NOT NULL,
    CONSTRAINT pk_logwalletminting PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin;

CREATE INDEX idx_logWalletMinting_walletAddress_eventUuid ON log_wallet_minting (wallet_address, event_uuid);
