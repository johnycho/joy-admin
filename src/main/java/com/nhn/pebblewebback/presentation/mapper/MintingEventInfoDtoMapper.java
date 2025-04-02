package com.nhn.pebblewebback.presentation.mapper;

import com.nhn.pebblewebback.application.config.PebbleWebMapperConfig;
import com.nhn.pebblewebback.application.dto.MintingEventDto.MintingEventRestResponse;
import com.nhn.pebblewebback.domain.vo.MintingEventInfo;
import org.mapstruct.Mapper;

/**
 * MintingEventInfoDtoMapper.
 */
@Mapper(config = PebbleWebMapperConfig.class)
public interface MintingEventInfoDtoMapper extends DtoMapper<MintingEventRestResponse, MintingEventInfo> {
}
