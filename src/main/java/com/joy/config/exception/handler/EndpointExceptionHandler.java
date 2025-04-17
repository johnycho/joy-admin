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
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@Aspect
@Component
public class EndpointExceptionHandler {

  @Around("within(com.joy.web.calendar.presentation.controller.rest..*)")
  public Object handleEndpointException(final ProceedingJoinPoint pjp) {
    final Object result;
    try {
      result = pjp.proceed();
    } catch (Throwable t) {
      return handleException(t);
    }
    return result;
  }

  @NotNull
  private ResponseEntity<ExceptionResponse> handleException(final Throwable t) {
    if (t instanceof CustomException e) {
      return handleCustomException(e);
    } else if (t instanceof HttpRequestMethodNotSupportedException e) {
      return handleCustomException(buildMethodNotSupported(e));
    } else if (t instanceof MissingServletRequestParameterException e) {
      return handleCustomException(buildMissingServletRequestParameter(e));
    } else if (t instanceof HttpMessageNotReadableException e) {
      return handleCustomException(buildNotReadableException(e));
    } else if (t instanceof MethodArgumentNotValidException e) {
      return handleCustomException(buildMethodArgumentNotValidException(e));
    } else if (t instanceof MethodArgumentTypeMismatchException e) {
      return handleCustomException(buildMethodArgumentTypeMismatch(e));
    }
    return handleCustomException(buildExtraException((Exception) t));
  }

  /**
   * Exception 을 커스터마이징 하고, ContentLoggingFilter 에 로깅 전파.
   *
   * @param exception 커스텀 예외
   * @return ExceptionResponse DTO 타입의 ResponseEntity를 리턴한다.
   */
  private ResponseEntity<ExceptionResponse> handleCustomException(final CustomException exception) {
    ContentLoggingFilter.setException(exception);
    return ExceptionResponse.of(exception);
  }
}
