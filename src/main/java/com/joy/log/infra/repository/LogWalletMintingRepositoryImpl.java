package com.joy.log.infra.repository;

import com.joy.log.domain.entity.LogWalletMinting;
import com.joy.log.domain.repository.LogWalletMintingRepository;
import com.joy.log.infra.jpa.LogWalletMintingJpa;
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
