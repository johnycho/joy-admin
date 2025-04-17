package com.joy.config.mapper;

/**
 * Entity -> DTO 단방향 Mapper.
 */
public interface DtoMapper<D, E> {
  D toDto(E e);
}
