package com.nhn.pebblewebback.application.service;

import com.nhn.pebblewebback.application.dto.LogGiveawayClaimDto.LogGiveawayClaimRequest;
import com.nhn.pebblewebback.application.dto.LogWalletMintingDto.LogWalletMintingRequest;
import com.nhn.pebblewebback.domain.repository.LogGiveawayClaimRepository;
import com.nhn.pebblewebback.domain.repository.LogWalletMintingRepository;
import com.nhn.pebblewebback.presentation.mapper.LogGiveawayClaimMapper;
import com.nhn.pebblewebback.presentation.mapper.LogWalletMintingMapper;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 로깅 서비스.
 */
@Service
@RequiredArgsConstructor
public class LoggingService {

  private final LogWalletMintingMapper logWalletMintingMapper;
  private final LogWalletMintingRepository logWalletMintingRepository;
  private final LogGiveawayClaimMapper logGiveawayClaimMapper;
  private final LogGiveawayClaimRepository logGiveawayClaimRepository;

  @Transactional
  public Boolean addLogWalletMinting(final LogWalletMintingRequest request) {
    return Objects.nonNull(logWalletMintingRepository.save(logWalletMintingMapper.toEntity(request)).getId());
  }

  @Transactional
  public Boolean addLogGiveawayClaim(final LogGiveawayClaimRequest request) {
    return Objects.nonNull(logGiveawayClaimRepository.save(logGiveawayClaimMapper.toEntity(request)).getId());
  }
}
