package com.nhn.pebblewebback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@EnableFeignClients
@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = "com.nhn.pebblewebback")
public class PebbleWebBackApplication {

  public static void main(String[] args) {
    SpringApplication.run(PebbleWebBackApplication.class, args);
  }

  @Bean
  public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
    return new HiddenHttpMethodFilter();
  }
}
