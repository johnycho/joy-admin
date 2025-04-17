package com.joy.config.util;

import com.joy.config.exception.constant.ExceptionInfo;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.TimeZone;

public final class DateTimeUtils {

  private static final SimpleDateFormat DATE_FORMAT;

  static {
    DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
  }

  private DateTimeUtils() {
    throw ExceptionInfo.NOT_ALLOWED_CONSTRUCTOR_CALL.exception();
  }

  public static LocalDateTime convertToKstDateTime(final LocalDateTime localDateTime) {
    return LocalDateTime.parse(DATE_FORMAT.format(Timestamp.valueOf(localDateTime)));
  }
}
