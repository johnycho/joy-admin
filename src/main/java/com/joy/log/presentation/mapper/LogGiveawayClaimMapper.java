package com.joy.log.presentation.mapper;

import com.joy.config.JoyMapperConfig;
import com.joy.config.mapper.EntityMapper;
import com.joy.config.util.NetworkUtil;
import com.joy.log.application.dto.LogGiveawayClaimDto.LogGiveawayClaimRequest;
import com.joy.log.domain.entity.LogGiveawayClaim;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * LogGiveawayClaimMapper.
 */
@Mapper(config = JoyMapperConfig.class, imports = { NetworkUtil.class })
public interface LogGiveawayClaimMapper extends EntityMapper<LogGiveawayClaimRequest, LogGiveawayClaim> {

  @Mapping(target = "ipAddress", expression = "java(NetworkUtil.getClientIp())")
  LogGiveawayClaim toEntity(LogGiveawayClaimRequest request);
}
