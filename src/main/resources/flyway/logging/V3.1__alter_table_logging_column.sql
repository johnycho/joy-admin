ALTER TABLE log_wallet_minting MODIFY COLUMN wallet_address VARCHAR(100) COLLATE utf8mb4_bin NOT NULL;
ALTER TABLE log_giveaway_claim MODIFY COLUMN sui_address VARCHAR(100) COLLATE utf8mb4_bin NULL;
