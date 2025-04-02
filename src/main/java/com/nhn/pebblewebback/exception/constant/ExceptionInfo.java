package com.nhn.pebblewebback.exception.constant;

import com.nhn.pebblewebback.exception.constant.Category.Feature;
import com.nhn.pebblewebback.exception.constant.Category.Service;
import com.nhn.pebblewebback.exception.vo.CustomException;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionInfo implements ErrorInformation {

  NOT_ALLOWED_CONSTRUCTOR_CALL(HttpStatus.INTERNAL_SERVER_ERROR, "Not allowed constructor call.", ErrorLevel.CRITICAL),
  NOT_ALLOWED_TOKEN_RANGE_OVERLAPPED(HttpStatus.BAD_REQUEST, "Token ID 대역이 겹치는 이벤트가 존재합니다.", ErrorLevel.WARNING),
  NOT_ALLOWED_MINTING_EVENT_PERIOD_OVERLAPPED(HttpStatus.BAD_REQUEST, "기간이 겹치는 이벤트가 존재합니다.", ErrorLevel.WARNING),
  INVALID_MINTING_EVENT_PERIOD(HttpStatus.BAD_REQUEST, "이벤트 기간이 유효하지 않습니다.", ErrorLevel.CRITICAL),
  INVALID_WHITE_LIST_FILE(HttpStatus.BAD_REQUEST, "파일이 유효하지 않습니다.", ErrorLevel.CRITICAL),
  INVALID_EVENT_TYPE(HttpStatus.BAD_REQUEST, "이벤트 타입이 유효하지 않습니다.", ErrorLevel.CRITICAL),
  NOT_FOUND_SERVICE(HttpStatus.NOT_FOUND, "해당 서비스가 존재하지 않습니다.", ErrorLevel.CRITICAL),
  NOT_FOUND_MINTING_EVENT(HttpStatus.NOT_FOUND, "이벤트가 존재하지 않습니다.", ErrorLevel.CRITICAL),
  NOT_FOUND_TOKEN_IDENTIFIER(HttpStatus.NOT_FOUND, "Token ID 정보가 존재하지 않습니다.", ErrorLevel.CRITICAL),
  NOT_ENOUGH_TOKEN_IDENTIFIER(HttpStatus.SERVICE_UNAVAILABLE, "할당 가능한 Token ID가 부족합니다.", ErrorLevel.CRITICAL),
  UNAUTHORIZED_ADDRESS(HttpStatus.UNAUTHORIZED, "권한이 없는 지갑 주소입니다.", ErrorLevel.WARNING),
  UNAVAILABLE_NOW(HttpStatus.SERVICE_UNAVAILABLE, "진행중인 이벤트가 없습니다.", ErrorLevel.WARNING),
  UNEXPECTED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected exception occurred.", ErrorLevel.CRITICAL),
  ;

  private final HttpStatus status;
  private final String message;
  private final ErrorLevel level;

  public CustomException exception() {
    return CustomException.builder()
                          .service(Service.PEBBLE)
                          .features(Set.of(Feature.PEBBLE))
                          .errorCode(this)
                          .build();
  }
}
