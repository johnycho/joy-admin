package com.joy.config.exception.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.joy.config.exception.constant.Category;
import com.joy.config.exception.constant.Category.Feature;
import com.joy.config.exception.constant.Category.Service;
import com.joy.config.exception.constant.ErrorInformation;
import com.joy.config.exception.constant.ErrorLevel;
import com.joy.config.util.ProfileUtils;
import jakarta.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.apache.commons.lang3.StringUtils;

@Getter
public class CustomException extends RuntimeException {

  private static final Set<Feature> FEATURE_ETC = Set.of(Feature.ETC);

  private int statusCode;
  private String errorMessage;
  private ErrorLevel errorlevel;
  private Category.Service service;
  private Set<Feature> features;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Map<String, Object> params;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Map<String, Object> debugs;

  @Builder
  public CustomException(@Nullable Throwable rootCause,
                         ErrorInformation errorCode, Service service, @Singular Set<Feature> features,
                         @Singular Map<String, Object> params, Supplier<Map<String, Object>> debugs,
                         String message, ErrorLevel level) {
    super(bestMessage(errorCode.getMessage(), message), rootCause);
    init(errorCode.getStatus().value(), errorCode.getMessage(), errorCode.getLevel(),
         service, features, params, debugs, message, level);
  }

  public static CustomExceptionBuilder builder() {
    return new CustomExceptionBuilder();
  }

  public static CustomExceptionBuilder builder(Throwable rootCause) {
    Objects.requireNonNull(rootCause);
    return builder().rootCause(rootCause);
  }

  /**
   * 정의된 문구가 아닌 별도의 문구로 노출해주기 위함.
   */
  private static String bestMessage(String errorMessage, String message) {
    if (StringUtils.isNotBlank(message)) {
      return message;
    }
    return Objects.requireNonNull(errorMessage);
  }

  @Nullable
  private static Map<String, Object> bestDebugs(Supplier<Map<String, Object>> debugs) {
    if (ProfileUtils.isReal()) {
      return null;
    }
    if (debugs == null) {
      return null;
    }
    return debugs.get();
  }

  private void init(int statusCode, String errorMessage, ErrorLevel errorlevel,
                    @Nullable Service service, @Nullable Set<Feature> features,
                    @Nullable Map<String, Object> params, @Nullable Supplier<Map<String, Object>> debugs,
                    @Nullable String message, @Nullable ErrorLevel level) {
    this.statusCode = statusCode;
    this.errorMessage = bestMessage(errorMessage, message);
    this.errorlevel = Objects.requireNonNullElse(errorlevel, level);
    this.service = Objects.requireNonNullElse(service, Service.ETC);
    this.features = Objects.requireNonNullElse(features, FEATURE_ETC);
    this.params = params;
    this.debugs = bestDebugs(debugs);
  }
}
