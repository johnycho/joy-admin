package com.joy.log.infra.jpa;

import com.joy.log.domain.entity.LogGiveawayClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogGiveawayClaimJpa extends JpaRepository<LogGiveawayClaim, Long> {

}
