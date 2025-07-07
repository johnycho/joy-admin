package com.joy.config.util;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

/**
 * StringUtil.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

  /**
   * format 문자열 생성.
   *
   * @param format {} 사용 하는 logback format.
   * @param params 바인딩 parameters
   * @return string
   */
  public static String make(String format, Object... params) {
    return MessageFormatter.arrayFormat(format, params).getMessage();
  }

  /**
   * "{index}" format 문자열 생성.
   * index 는 0 이상.
   * 주의.
   * - "{}" 존재시 exception.
   * - 미존재 index 지정 시 그대로 출력. "{10}" -> "{10}"
   *
   * @param format "{0}, {1}, {2}, ..."
   * @param params 바인딩 parameters
   * @return string
   */
  public static String makeWithPlaceholder(String format, Object... params) {
    return MessageFormat.format(format, params);
  }

  /**
   * 숫자 1000 자리 마다 comma 가 붙은 string 으로 변환.
   * ex> 300000 -> 300,000
   *
   * @param n number
   * @return format string
   */
  public static String makeThousandCommaFormat(Long n) {
    return NumberFormat.getNumberInstance(Locale.US).format(n);
  }

  public static String requireNotBlank(String s) {
    if (StringUtils.isNotBlank(s)) {
      return s;
    }
    throw new IllegalArgumentException("parameter must not be a blank string");
  }

  public static boolean equals(String str1, String str2) {
    // null 이면 평가하지 않음
    if (str1 == null || str2 == null) {
      return false;
    }

    return str1.equals(str2);
  }

  public static boolean notEquals(String str1, String str2) {
    return !equals(str1, str2);
  }

  public static String makeCommaFormat(double number) {
    final String[] parts = BigDecimal.valueOf(number).toPlainString().split("\\.");
    final String integerPart = parts[0];
    final StringBuilder sb = new StringBuilder();

    final int len = integerPart.length();
    final int commaPos = len % 3;

    for (int i = 0; i < len; i++) {
      if (i > 0 && (i - commaPos) % 3 == 0) {
        sb.append(",");
      }
      sb.append(integerPart.charAt(i));
    }

    if (parts.length > 1) {
      final String fractional = parts[1].replaceAll("0+$", "");
      if (!fractional.isEmpty()) {  // 소수점 이하가 0만 아니라면 붙임
        sb.append(".").append(fractional);
      }
    }

    return sb.toString();
  }
}
