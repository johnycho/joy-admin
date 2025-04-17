package com.joy.log.infra.jpa;

import com.joy.log.domain.entity.LogWalletMinting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogWalletMintingJpa extends JpaRepository<LogWalletMinting, Long> {

}
