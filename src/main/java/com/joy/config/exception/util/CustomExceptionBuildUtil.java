package com.joy.config.exception.util;

import com.joy.config.exception.constant.PlatformErrorInfo;
import com.joy.config.exception.vo.CustomException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 자주 사용되는 CustomException 객체를 만들기 위해 사용함.
 */
public final class CustomExceptionBuildUtil {

  private CustomExceptionBuildUtil() {
    throw new AssertionError("Couldn't invoke private constructor.");
  }

  /**
   * 지원하지 않는 메소드 요청에 대한 Exception.
   */
  public static CustomException buildMethodNotSupported(final HttpRequestMethodNotSupportedException rootCause) {
    return CustomException.builder()
                          .errorCode(PlatformErrorInfo.BAD_REQUEST)
                          .rootCause(rootCause)
                          .param("method", rootCause.getMethod())
                          .build();
  }

  public static CustomException buildMissingServletRequestParameter(final MissingServletRequestParameterException rootCause) {
    return CustomException.builder()
                          .errorCode(PlatformErrorInfo.BAD_REQUEST)
                          .rootCause(rootCause)
                          .param("parameter", rootCause.getParameterName())
                          .build();
  }

  public static CustomException buildNotReadableException(final HttpMessageNotReadableException rootCause) {
    return CustomException.builder(rootCause)
                          .errorCode(PlatformErrorInfo.NOT_READABLE_DATA)
                          .debugs(() -> Map.of("detail_messages", Objects.requireNonNullElse(rootCause.getMessage(), "unknown")))
                          .build();
  }

  /**
   * 값 유효성 검사 Exception.
   *
   * @param rootCause 예외
   * @return ExceptionResponse
   */
  public static CustomException buildMethodArgumentNotValidException(final MethodArgumentNotValidException rootCause) {
    final Map<String, String> params = rootCause.getBindingResult().getFieldErrors()
                                                .stream()
                                                .filter(fieldError -> Objects.nonNull(fieldError.getDefaultMessage()))
                                                .collect(Collectors.toMap(FieldError::getField,
                                                                          DefaultMessageSourceResolvable::getDefaultMessage));

    return CustomException.builder(rootCause)
                          .errorCode(PlatformErrorInfo.VALIDATION_ERROR)
                          .params(params)
                          .message(params.values().toString())
                          .build();
  }

  public static CustomException buildMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException rootCause) {
    return CustomException.builder(rootCause)
                          .errorCode(PlatformErrorInfo.VALIDATION_ERROR)
                          .debugs(() -> Map.of("detail_messages", Objects.requireNonNullElse(rootCause.getMessage(), "unknown")))
                          .build();
  }

  /**
   * 커스텀 예외가 아닌 기타 예외를 처리하는 공통 Exception.
   *
   * @param rootCause 예외
   * @return 에러 페이지
   */
  public static CustomException buildExtraException(final Exception rootCause) {
    return ExceptionUtils.getRootCause(rootCause) instanceof CustomException ce ? ce : CustomException.builder(rootCause).errorCode(PlatformErrorInfo.UNKNOWN_ERROR).build();
  }
}
