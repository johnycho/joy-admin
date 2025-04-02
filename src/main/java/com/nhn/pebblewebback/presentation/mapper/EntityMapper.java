package com.nhn.pebblewebback.presentation.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * DTO -> Entity 단방향 Mapper.
 */
public interface EntityMapper<D, E> {
  E toEntity(D d);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateFromDto(D dto, @MappingTarget E entity);
}
