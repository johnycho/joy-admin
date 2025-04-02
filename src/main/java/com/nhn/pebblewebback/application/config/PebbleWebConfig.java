package com.nhn.pebblewebback.application.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * PebbleWebConfig.
 */
@Configuration
public class PebbleWebConfig {

  public static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule())
                                                              .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                                                              .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  @Bean(name = "commonObjectMapper")
  public ObjectMapper objectMapper() {
    return MAPPER;
  }

}
