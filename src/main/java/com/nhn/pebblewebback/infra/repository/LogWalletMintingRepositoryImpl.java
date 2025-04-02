package com.nhn.pebblewebback.infra.repository;

import com.nhn.pebblewebback.domain.entity.logging.LogWalletMinting;
import com.nhn.pebblewebback.domain.repository.LogWalletMintingRepository;
import com.nhn.pebblewebback.infra.repository.jpa.logging.LogWalletMintingJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LogWalletMintingRepositoryImpl implements LogWalletMintingRepository {

  private final LogWalletMintingJpa logWalletMintingJpa;

  @Override
  public LogWalletMinting save(final LogWalletMinting logWalletMinting) {
    return logWalletMintingJpa.save(logWalletMinting);
  }
}
