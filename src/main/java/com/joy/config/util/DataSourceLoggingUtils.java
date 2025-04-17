package com.joy.config.util;

import com.joy.config.exception.constant.ExceptionInfo;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.Nullable;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import net.ttddyy.dsproxy.listener.logging.Log4jLogLevel;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;

public final class DataSourceLoggingUtils {

  private DataSourceLoggingUtils() {
    throw ExceptionInfo.NOT_ALLOWED_CONSTRUCTOR_CALL.exception();
  }

  public static DataSource apply(DataSource dataSource) {
    return new ProxyDataSourceBuilder().dataSource(dataSource)
                                       .name(name(dataSource))
                                       .logQueryBySlf4j(SLF4JLogLevel.INFO)
                                       .logSlowQueryByLog4j(1L, TimeUnit.SECONDS, Log4jLogLevel.ERROR)
                                       .multiline()
                                       .build();
  }

  @Nullable
  private static String name(DataSource dataSource) {
    if (dataSource instanceof HikariDataSource h) {
      return h.getPoolName();
    }
    return null;
  }
}
