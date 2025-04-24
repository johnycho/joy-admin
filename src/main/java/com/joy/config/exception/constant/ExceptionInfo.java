package com.joy.config.exception.constant;

import com.joy.config.exception.constant.Category.Feature;
import com.joy.config.exception.constant.Category.Service;
import com.joy.config.exception.vo.CustomException;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionInfo implements ErrorInformation {

  NOT_ALLOWED_CONSTRUCTOR_CALL(HttpStatus.INTERNAL_SERVER_ERROR, "Not allowed constructor call.", ErrorLevel.CRITICAL),
  NOT_ALLOWED_MINTING_EVENT_PERIOD_OVERLAPPED(HttpStatus.BAD_REQUEST, "기간이 겹치는 이벤트가 존재합니다.", ErrorLevel.WARNING),
  INVALID_MINTING_EVENT_PERIOD(HttpStatus.BAD_REQUEST, "이벤트 기간이 유효하지 않습니다.", ErrorLevel.CRITICAL),
  INVALID_WHITE_LIST_FILE(HttpStatus.BAD_REQUEST, "파일이 유효하지 않습니다.", ErrorLevel.CRITICAL),
  INVALID_EVENT_TYPE(HttpStatus.BAD_REQUEST, "이벤트 타입이 유효하지 않습니다.", ErrorLevel.CRITICAL),
  NOT_FOUND_SERVICE(HttpStatus.NOT_FOUND, "해당 서비스가 존재하지 않습니다.", ErrorLevel.CRITICAL),
  NOT_FOUND_MINTING_EVENT(HttpStatus.NOT_FOUND, "이벤트가 존재하지 않습니다.", ErrorLevel.CRITICAL),
  INVALID_EVENT_DATETIME(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid event datetime.", ErrorLevel.CRITICAL),
  FAILED_TO_FETCH_CALENDAR(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch calendar.", ErrorLevel.CRITICAL),
  FAILED_TO_FETCH_CALENDAR_INFO(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch calendar info: {}.", ErrorLevel.CRITICAL),
  FAILED_TO_FETCH_CALENDAR_TYPES(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch calendar types.", ErrorLevel.CRITICAL),
  FAILED_TO_FETCH_CALENDAR_EVENTS(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch calendar events: {}.", ErrorLevel.CRITICAL),
  UNEXPECTED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected exception occurred.", ErrorLevel.CRITICAL),
  ;

  private final HttpStatus status;
  private final String message;
  private final ErrorLevel level;

  public CustomException exception() {
    return CustomException.builder()
                          .service(Service.JOY)
                          .features(Set.of(Feature.JOY))
                          .errorCode(this)
                          .build();
  }
}
