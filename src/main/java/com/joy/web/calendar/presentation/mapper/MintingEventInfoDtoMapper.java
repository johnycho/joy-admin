package com.joy.web.calendar.presentation.mapper;

import com.joy.config.JoyMapperConfig;
import com.joy.config.mapper.DtoMapper;
import com.joy.web.calendar.application.dto.MintingEventDto.MintingEventRestResponse;
import com.joy.web.calendar.domain.vo.MintingEventInfo;
import org.mapstruct.Mapper;

/**
 * MintingEventInfoDtoMapper.
 */
@Mapper(config = JoyMapperConfig.class)
public interface MintingEventInfoDtoMapper extends DtoMapper<MintingEventRestResponse, MintingEventInfo> {
}
