package com.joy.config.security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joy.config.JoyWebConfig;
import com.joy.config.exception.constant.ErrorLevel;
import com.joy.config.exception.constant.ExceptionInfo;
import com.joy.config.exception.vo.CustomException;
import com.joy.config.util.NetworkUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContentLoggingFilter extends OncePerRequestFilter {

  private static final ObjectMapper MAPPER = JoyWebConfig.MAPPER;
  private static final String RESPONSE_LOG_FORMAT = "[request:{}] [response:{}]";
  private static final ThreadLocal<Exception> threadLocalException = new ThreadLocal<>();

  public static void setException(Exception e) {
    threadLocalException.set(e);
  }

  /**
   * 로깅.
   *
   * @param requestLog  requestLogVo
   * @param responseLog responseLogVo
   */
  private void logResponse(RequestLog requestLog, ResponseLog responseLog) {

    try {
      final Exception e = threadLocalException.get();
      final String request = MAPPER.writeValueAsString(requestLog.data());
      final String response = MAPPER.writeValueAsString(responseLog);

      if (e == null) {
        if (responseLog.httpCode() >= HttpStatus.BAD_REQUEST.value()) {
          log.warn(RESPONSE_LOG_FORMAT, request, response);
        } else {
          log.debug(RESPONSE_LOG_FORMAT, request, response);
        }
        return;
      }

      if (e instanceof final CustomException customException) {
        if (customException.getErrorlevel() == ErrorLevel.CRITICAL) {
          log.error(RESPONSE_LOG_FORMAT, request, response, e);
        } else if (customException.getErrorlevel() == ErrorLevel.WARNING) {
          log.warn(RESPONSE_LOG_FORMAT, request, response, e);
        } else if (customException.getErrorlevel() == ErrorLevel.NORMAL) {
          log.info(RESPONSE_LOG_FORMAT, request, response, e);
        }
        return;
      }
      log.error(RESPONSE_LOG_FORMAT, request, response, e);
    } catch (Exception e) {
      log.error("fail response logging.", e);
    } finally {
      threadLocalException.remove();
    }

  }

  /**
   * ContentCachingRequestWrapper 생성.
   *
   * @param request request
   * @return ContentCachingRequestWrapper
   */
  private ContentCachingRequestWrapper wrapCachingRequest(HttpServletRequest request) {

    if (request instanceof ContentCachingRequestWrapper) {
      return (ContentCachingRequestWrapper) request;
    } else {
      return new ContentCachingRequestWrapper(request);
    }
  }

  /**
   * ContentCachingResponseWrapper 생성.
   *
   * @param response response
   * @return ContentCachingResponseWrapper
   */
  private ContentCachingResponseWrapper wrapCachingResponse(HttpServletResponse response) {
    // 주의. res.copyBodyToResponse(); 해야지 실제 response 로 전송 된다.
    if (response instanceof ContentCachingResponseWrapper) {
      return (ContentCachingResponseWrapper) response;

    } else {
      return new ContentCachingResponseWrapper(response);
    }
  }

  /**
   * json request, response body 추출.
   *
   * @param content     content
   * @param contentType contentType
   * @return map
   */
  private Map<String, Object> getBody(byte[] content, String contentType) {

    try {

      // 파일인 경우 제외
      if (StringUtils.containsIgnoreCase(contentType, MediaType.MULTIPART_FORM_DATA_VALUE)) {
        return Map.of("data", "multi part form data");
      }

      final String str = new String(content, StandardCharsets.UTF_8);

      final var map = convertToMap(contentType, str);
      if (!map.isEmpty()) {
        return map;
      }

      final var newMap = new HashMap<String, Object>();
      newMap.put("data", str);
      return newMap;

    } catch (Exception e) {
      log.error("getBody", e);
    }
    return new HashMap<>();
  }

  private Map<String, Object> convertToMap(String type, String str) {

    try {
      if (StringUtils.containsIgnoreCase(type, "json")) {

        // 응답이 배열인 경우
        if (StringUtils.startsWith(str, "[")) {
          return Map.of("list", JoyWebConfig.MAPPER.readValue(str, List.class));
        }

        return JoyWebConfig.MAPPER.readValue(str, new TypeReference<>() {
        });
      }
    } catch (JsonProcessingException e) {
      // nothing to do : request 의 json 포멧이 잘못된 경우임
    }
    return new HashMap<>();
  }

  /**
   * request 데이터 추출.
   *
   * @param httpServletRequest httpServletRequest
   * @return map
   */
  private Map<String, Object> getRequestData(final HttpServletRequest httpServletRequest) {

    if (!(httpServletRequest instanceof final ContentCachingRequestWrapper req)) {
      return Map.of("desc", "is not ContentCachingRequestWrapper");
    }

    // request
    final Map<String, Object> map = new LinkedHashMap<>();
    map.put("url", req.getRequestURL().toString());
    map.put("queryString", req.getQueryString());
    map.put("method", req.getMethod());
    map.put("clientIp", NetworkUtil.getClientIp(req));
    map.put("parameters", copy(req.getParameterMap()));
    map.put("body", getBody(req.getContentAsByteArray(), req.getContentType()));

    return map;
  }

  private Map<String, String[]> copy(Map<String, String[]> map) {

    return map.entrySet()
              .stream()
              .collect(Collectors.toMap(
                  Map.Entry::getKey,
                  Map.Entry::getValue
              ));
  }

  /**
   * response 정보 추출.
   *
   * @param res response
   * @return map
   */
  private Map<String, Object> getResponseData(final HttpServletResponse res) {

    final Map<String, Object> map = new LinkedHashMap<>();
    map.put("status", res.getStatus());

    final String contentType = res.getContentType();
    if (StringUtils.contains(contentType, "json")) {
      // ContentCachingResponseWrapper 가 아닌 경우는
      // response 값을 읽게 되면 response 를 보내지 못하게 된다.
      if (res instanceof ContentCachingResponseWrapper) {
        map.put("body", getBody(((ContentCachingResponseWrapper) res).getContentAsByteArray(), res.getContentType()));
      } else {
        map.put("body", "is not ContentCachingResponseWrapper");
      }
      return map;
    }
    if (contentType != null) {
      map.put("body", Map.of("contentType", contentType));
    }
    return map;
  }

  record RequestLog(Map<String, Object> data) {

  }

  record ResponseLog(int httpCode,
                     long elapsedTime,
                     Map<String, Object> data) {

  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws IOException {

    final long startTime = System.currentTimeMillis();
    threadLocalException.remove();

    // 만약, 대용량 파일 전송시 ContentCachingResponseWrapper 를 쓰면 안된다.
    // 그런 경우 설정을 통해 변경 한다.
    final ContentCachingRequestWrapper requestWrapper = wrapCachingRequest(request);
    final ContentCachingResponseWrapper responseWrapper = wrapCachingResponse(response);

    try {

      filterChain.doFilter(requestWrapper, responseWrapper);

    } catch (Exception e) {
      setException(e);

      // response error
      responseWrapper.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseWrapper.setContentType(MediaType.APPLICATION_JSON_VALUE);
      responseWrapper.setCharacterEncoding(StandardCharsets.UTF_8.name());
      responseWrapper.getWriter().print(
          MAPPER.writeValueAsString(
              ExceptionInfo.UNEXPECTED_EXCEPTION.exception()));

    } finally {
      // response 로그
      logResponse(
          new RequestLog(getRequestData(requestWrapper)),
          new ResponseLog(
              responseWrapper.getStatus(),
              System.currentTimeMillis() - startTime,
              getResponseData(responseWrapper))
      );

      // ContentCachingResponseWrapper 사용 하는 경우 필수
      responseWrapper.copyBodyToResponse();
    }
  }
}
