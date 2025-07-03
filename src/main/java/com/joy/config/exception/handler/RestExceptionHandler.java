package com.joy.config.exception.handler;

import com.joy.config.exception.dto.ExceptionResponse;
import static com.joy.config.exception.util.CustomExceptionBuildUtil.buildExtraException;
import static com.joy.config.exception.util.CustomExceptionBuildUtil.buildMethodArgumentNotValidException;
import static com.joy.config.exception.util.CustomExceptionBuildUtil.buildMethodArgumentTypeMismatch;
import static com.joy.config.exception.util.CustomExceptionBuildUtil.buildMethodNotSupported;
import static com.joy.config.exception.util.CustomExceptionBuildUtil.buildMissingServletRequestParameter;
import static com.joy.config.exception.util.CustomExceptionBuildUtil.buildNotReadableException;
import com.joy.config.exception.vo.CustomException;
import com.joy.config.security.filter.ContentLoggingFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice(annotations = { RestController.class })
@Order(1)
public class RestExceptionHandler {

  /**
   * 커스텀 예외를 처리하는 공통 핸들러.
   *
   * @param exception 커스텀 예외
   * @return ExceptionResponse DTO 타입의 ResponseEntity를 리턴한다.
   */
  @ExceptionHandler({ CustomException.class })
  public ResponseEntity<ExceptionResponse> processCustomException(final CustomException exception) {
    ContentLoggingFilter.setException(exception);
    return ExceptionResponse.of(exception);
  }

  /**
   * 지원하지 않는 메소드 요청에 대한 공통 핸들러.
   */
  @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
  public ResponseEntity<ExceptionResponse> processMethodNotSupported(final HttpRequestMethodNotSupportedException rootCause) {
    return processCustomException(buildMethodNotSupported(rootCause));
  }

  @ExceptionHandler({ MissingServletRequestParameterException.class })
  public ResponseEntity<ExceptionResponse> processMissingServletRequestParameter(final MissingServletRequestParameterException rootCause) {
    return processCustomException(buildMissingServletRequestParameter(rootCause));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ExceptionResponse> handleNotReadableException(final HttpMessageNotReadableException rootCause) {
    return processCustomException(buildNotReadableException(rootCause));
  }

  /**
   * 값 유효성 검사 핸들러.
   *
   * @param rootCause 예외
   * @return ExceptionResponse
   */
  @ExceptionHandler({ MethodArgumentNotValidException.class })
  public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException rootCause) {
    return processCustomException(buildMethodArgumentNotValidException(rootCause));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException rootCause) {
    return processCustomException(buildMethodArgumentTypeMismatch(rootCause));
  }

  /**
   * 커스텀 예외가 아닌 기타 예외를 처리하는 공통 핸들러.
   *
   * @param rootCause 예외
   * @return 에러 페이지
   */
  @ExceptionHandler({ RuntimeException.class, Exception.class })
  public ResponseEntity<ExceptionResponse> processExtraException(final Exception rootCause) {
    return processCustomException(buildExtraException(rootCause));
  }
}
