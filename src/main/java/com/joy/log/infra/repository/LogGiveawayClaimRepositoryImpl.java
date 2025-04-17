package com.joy.log.infra.repository;

import com.joy.log.domain.entity.LogGiveawayClaim;
import com.joy.log.domain.repository.LogGiveawayClaimRepository;
import com.joy.log.infra.jpa.LogGiveawayClaimJpa;
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
