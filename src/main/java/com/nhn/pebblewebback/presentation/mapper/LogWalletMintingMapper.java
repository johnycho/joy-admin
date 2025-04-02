package com.nhn.pebblewebback.presentation.mapper;

import com.nhn.pebblewebback.application.config.PebbleWebMapperConfig;
import com.nhn.pebblewebback.application.dto.LogWalletMintingDto.LogWalletMintingRequest;
import com.nhn.pebblewebback.application.util.NetworkUtil;
import com.nhn.pebblewebback.domain.entity.logging.LogWalletMinting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * LogWalletMintingMapper.
 */
@Mapper(config = PebbleWebMapperConfig.class, imports = { NetworkUtil.class })
public interface LogWalletMintingMapper extends EntityMapper<LogWalletMintingRequest, LogWalletMinting> {

  @Mapping(target = "ipAddress", expression = "java(NetworkUtil.getClientIp())")
  LogWalletMinting toEntity(LogWalletMintingRequest request);
}
