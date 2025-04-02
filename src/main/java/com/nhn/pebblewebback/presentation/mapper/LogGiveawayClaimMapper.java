package com.nhn.pebblewebback.presentation.mapper;

import com.nhn.pebblewebback.application.config.PebbleWebMapperConfig;
import com.nhn.pebblewebback.application.dto.LogGiveawayClaimDto.LogGiveawayClaimRequest;
import com.nhn.pebblewebback.application.util.NetworkUtil;
import com.nhn.pebblewebback.domain.entity.logging.LogGiveawayClaim;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * LogGiveawayClaimMapper.
 */
@Mapper(config = PebbleWebMapperConfig.class, imports = { NetworkUtil.class })
public interface LogGiveawayClaimMapper extends EntityMapper<LogGiveawayClaimRequest, LogGiveawayClaim> {

  @Mapping(target = "ipAddress", expression = "java(NetworkUtil.getClientIp())")
  LogGiveawayClaim toEntity(LogGiveawayClaimRequest request);
}
