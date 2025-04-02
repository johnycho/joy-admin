package com.nhn.pebblewebback.infra.repository.jpa.logging;

import com.nhn.pebblewebback.domain.entity.logging.LogGiveawayClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogGiveawayClaimJpa extends JpaRepository<LogGiveawayClaim, Long> {

}
