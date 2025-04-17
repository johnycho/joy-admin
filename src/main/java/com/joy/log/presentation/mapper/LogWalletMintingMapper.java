package com.joy.log.presentation.mapper;

import com.joy.config.JoyMapperConfig;
import com.joy.config.mapper.EntityMapper;
import com.joy.config.util.NetworkUtil;
import com.joy.log.application.dto.LogWalletMintingDto.LogWalletMintingRequest;
import com.joy.log.domain.entity.LogWalletMinting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * LogWalletMintingMapper.
 */
@Mapper(config = JoyMapperConfig.class, imports = { NetworkUtil.class })
public interface LogWalletMintingMapper extends EntityMapper<LogWalletMintingRequest, LogWalletMinting> {

  @Mapping(target = "ipAddress", expression = "java(NetworkUtil.getClientIp())")
  LogWalletMinting toEntity(LogWalletMintingRequest request);
}
