ALTER TABLE log_wallet_minting MODIFY COLUMN error_message VARCHAR(100) COLLATE utf8mb4_bin NULL;
ALTER TABLE log_wallet_minting MODIFY COLUMN token_id VARCHAR(50) COLLATE utf8mb4_bin NULL;
ALTER TABLE log_wallet_minting MODIFY COLUMN event_uuid VARCHAR(30) COLLATE utf8mb4_bin NOT NULL;

ALTER TABLE log_giveaway_claim MODIFY COLUMN error_message VARCHAR(100) COLLATE utf8mb4_bin NULL;
