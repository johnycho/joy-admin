package com.joy.config.exception.constant;

import lombok.RequiredArgsConstructor;

/**
 * 시스템 내에서 발생하는 다양한 예외 상황에 대한 오류 레벨을 나타냅니다.
 *
 * <p>
 * - {@code CRITICAL}: 데이터 흐름상 핵심적이고 반드시 존재해야 하는 데이터나 기본 프로덕트가 없을 때 발생하는 예외를 나타냅니다. 이러한 상황은 시스템 흐름상 큰 문제를 야기할 수 있기 때문에 방어 로직과 함께 예외를 발생시키는 경우에 이 레벨을 사용합니다.
 * </p>
 *
 * <p>
 * - {@code NORMAL}: 사용자의 요청 검증과 관련하여 발생하는 예외를 나타냅니다. 이는 예상되는 예외나 일반적인 예외 상황에 해당합니다.
 * </p>
 *
 * <p>
 * - {@code WARNING}: 결제와 같은 민감한 도메인에서 발생하는 예외를 나타냅니다. CRITICAL과 NORMAL 사이의 예외 상황에서 이 레벨을 사용할 수 있습니다.
 * </p>
 */
@RequiredArgsConstructor
public enum ErrorLevel {

  CRITICAL("심각한 예외"),
  WARNING("눈여겨 봐야하는 예외"),
  NORMAL("의도한 예외/일반"),
  ;

  private final String description;
}
