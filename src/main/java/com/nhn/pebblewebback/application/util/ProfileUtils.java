package com.nhn.pebblewebback.application.util;

import com.nhn.pebblewebback.exception.constant.ExceptionInfo;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

public final class ProfileUtils implements EnvironmentPostProcessor {

  private static final Set<String> activeProfiles = ConcurrentHashMap.newKeySet();

  private ProfileUtils() {
    throw ExceptionInfo.NOT_ALLOWED_CONSTRUCTOR_CALL.exception();
  }

  public static boolean isTest() {
    return activeProfiles.contains("test");
  }

  public static boolean isLocal() {
    return activeProfiles.contains("local");
  }

  public static boolean isDev() {
    return activeProfiles.contains("dev");
  }

  public static boolean isAlpha() {
    return activeProfiles.contains("alpha");
  }

  public static boolean isReal() {
    return activeProfiles.contains("real");
  }

  public static String findActiveProfile() {
    if (isReal()) {
      return "real";
    }
    if (isAlpha()) {
      return "alpha";
    }
    if (isDev()) {
      return "dev";
    }
    if (isLocal()) {
      return "local";
    }
    if (isTest()) {
      return "test";
    }

    throw new UnsupportedOperationException();
  }

  @SuppressFBWarnings
  @Override
  public void postProcessEnvironment(final ConfigurableEnvironment environment, final SpringApplication application) {
    activeProfiles.addAll(Arrays.stream(environment.getActiveProfiles())
                                .collect(Collectors.toUnmodifiableSet()));
  }
}
