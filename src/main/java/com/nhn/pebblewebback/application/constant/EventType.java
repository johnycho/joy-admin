package com.nhn.pebblewebback.application.constant;

import com.nhn.pebblewebback.exception.constant.ExceptionInfo;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EventType {
  COMMUNITY_NFT("cn"),
  GIVEAWAY("ga");

  private final String code;

  private static final Map<String, EventType> typeMap;

  static {
    typeMap = Arrays.stream(values()).collect(Collectors.toMap(EventType::getCode, Function.identity()));
  }

  public static EventType getTypeByCode(final String code) {
    return Optional.ofNullable(typeMap.get(code)).orElseThrow(ExceptionInfo.INVALID_EVENT_TYPE::exception);
  }
}
