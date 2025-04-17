package com.joy.web.calendar.presentation.mapper;

import com.joy.config.JoyMapperConfig;
import com.joy.config.mapper.EntityMapper;
import com.joy.web.calendar.application.dto.MintingEventDto.MintingEventMvcRequest;
import com.joy.web.calendar.domain.entity.MintingEvent;
import org.mapstruct.Mapper;

/**
 * MintingEventInfoDtoMapper.
 */
@Mapper(config = JoyMapperConfig.class)
public interface MintingEventEntityMapper extends EntityMapper<MintingEventMvcRequest, MintingEvent> {
}
