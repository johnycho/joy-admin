package com.nhn.pebblewebback.exception.constant;

import com.nhn.pebblewebback.exception.vo.CustomException;
import com.nhn.pebblewebback.exception.vo.CustomException.CustomExceptionBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PlatformErrorInfo implements ErrorInformation {

  UNKNOWN_ERROR("알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, ErrorLevel.CRITICAL),

  BAD_REQUEST("bad request", HttpStatus.BAD_REQUEST, ErrorLevel.NORMAL),

  VALIDATION_ERROR("올바르지 않은 요청 값이 포함되어 있습니다.", HttpStatus.BAD_REQUEST, ErrorLevel.NORMAL),
  NOT_READABLE_DATA("올바르지 않은 키(필드)/구조입니다.", HttpStatus.BAD_REQUEST, ErrorLevel.WARNING);

  private final String message;
  private final HttpStatus status;
  private final ErrorLevel level;

  public CustomExceptionBuilder builder() {
    return CustomException.builder().errorCode(this);
  }

  public CustomException exception() {
    return builder().build();
  }

  public CustomException exception(Throwable cause) {
    return builder().rootCause(cause).build();
  }
}
