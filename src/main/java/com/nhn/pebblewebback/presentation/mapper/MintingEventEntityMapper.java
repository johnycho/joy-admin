package com.nhn.pebblewebback.presentation.mapper;

import com.nhn.pebblewebback.application.config.PebbleWebMapperConfig;
import com.nhn.pebblewebback.application.dto.MintingEventDto.MintingEventMvcRequest;
import com.nhn.pebblewebback.domain.entity.web.MintingEvent;
import org.mapstruct.Mapper;

/**
 * MintingEventInfoDtoMapper.
 */
@Mapper(config = PebbleWebMapperConfig.class)
public interface MintingEventEntityMapper extends EntityMapper<MintingEventMvcRequest, MintingEvent> {
}
