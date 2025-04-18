package com.joy.config.mapper;

/**
 * DTO <-> Entity 양방향 Mapper.
 */
public interface DtoEntityMapper<D, E> extends DtoMapper<D, E>, EntityMapper<D, E> {
}
