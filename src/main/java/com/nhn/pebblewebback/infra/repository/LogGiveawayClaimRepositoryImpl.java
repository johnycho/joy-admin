package com.nhn.pebblewebback.infra.repository;

import com.nhn.pebblewebback.domain.entity.logging.LogGiveawayClaim;
import com.nhn.pebblewebback.domain.repository.LogGiveawayClaimRepository;
import com.nhn.pebblewebback.infra.repository.jpa.logging.LogGiveawayClaimJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LogGiveawayClaimRepositoryImpl implements LogGiveawayClaimRepository {

  private final LogGiveawayClaimJpa logGiveawayClaimJpa;

  @Override
  public LogGiveawayClaim save(final LogGiveawayClaim logGiveawayClaim) {
    return logGiveawayClaimJpa.save(logGiveawayClaim);
  }
}
