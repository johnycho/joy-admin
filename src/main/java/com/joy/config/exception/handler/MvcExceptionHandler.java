package com.joy.config.exception.handler;

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
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice(annotations = Controller.class)
@Order(2)
class MvcExceptionHandler {

  private static final String ERROR_PAGE = "error/error";

  /**
   * 커스텀 예외를 처리하는 공통 핸들러.
   *
   * @param model mvc 모델
   * @param exception 커스텀 예외
   * @return 에러 뷰 페이지
   */
  @ExceptionHandler({ CustomException.class })
  public String processCustomException(final Model model, final CustomException exception) {
    ContentLoggingFilter.setException(exception);
    applyExceptionToModel(model, exception);
    return ERROR_PAGE;
  }

  private static void applyExceptionToModel(final Model model, final CustomException exception) {
    model.addAttribute("status", exception.getStatusCode());
    model.addAttribute("message", exception.getErrorMessage());
  }

  /**
   * 지원하지 않는 메소드 요청에 대한 공통 핸들러.
   */
  @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
  public String processMethodNotSupported(final Model model, final HttpRequestMethodNotSupportedException rootCause) {
    return processCustomException(model, buildMethodNotSupported(rootCause));
  }

  @ExceptionHandler({ MissingServletRequestParameterException.class })
  public String processMissingServletRequestParameter(final Model model, final MissingServletRequestParameterException rootCause) {
    return processCustomException(model, buildMissingServletRequestParameter(rootCause));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public String handleNotReadableException(final Model model, final HttpMessageNotReadableException rootCause) {
    return processCustomException(model, buildNotReadableException(rootCause));
  }

  /**
   * 값 유효성 검사 핸들러.
   *
   * @param model mvc 모델
   * @param rootCause 예외
   * @return 에러 뷰 페이지
   */
  @ExceptionHandler({ MethodArgumentNotValidException.class })
  public String handleMethodArgumentNotValidException(final Model model, final MethodArgumentNotValidException rootCause) {
    return processCustomException(model, buildMethodArgumentNotValidException(rootCause));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public String handleMethodArgumentTypeMismatch(final Model model, final MethodArgumentTypeMismatchException rootCause) {
    return processCustomException(model, buildMethodArgumentTypeMismatch(rootCause));
  }

  /**
   * 커스텀 예외가 아닌 기타 예외를 처리하는 공통 핸들러.
   *
   * @param model mvc 모델
   * @param rootCause 예외
   * @return 에러 뷰 페이지
   */
  @ExceptionHandler({ RuntimeException.class, Exception.class })
  public String processExtraException(final Model model, final Exception rootCause) {
    return processCustomException(model, buildExtraException(rootCause));
  }
}
