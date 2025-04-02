package com.nhn.pebblewebback.domain.repository;

import com.nhn.pebblewebback.domain.entity.logging.LogWalletMinting;

public interface LogWalletMintingRepository {

  LogWalletMinting save(final LogWalletMinting logWalletMinting);
}
