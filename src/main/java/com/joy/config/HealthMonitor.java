package com.joy.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class HealthMonitor implements HealthIndicator {
  @Override
  public Health health() {
    return isHealthy()
           ? Health.up().build()
           : Health.down().build();
  }

  public boolean isHealthy() {
    return true;
  }
}
