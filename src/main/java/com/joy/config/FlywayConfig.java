package com.joy.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class FlywayConfig {

  private final DataSource dataSource;
  private final String location;

  @Value("${spring.profiles.active:}")
  private String activeProfile;

  public void migrateFlyway() {
    if (!"local".equals(activeProfile)) {
      return;
    }
    Flyway.configure()
          .dataSource(dataSource)
          .baselineOnMigrate(true)
          .outOfOrder(true)
          .locations(location)
          .load()
          .migrate();
  }
}
