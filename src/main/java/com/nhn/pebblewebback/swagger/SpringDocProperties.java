package com.nhn.pebblewebback.swagger;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerProperties.
 */
@Data
@Configuration
@ConfigurationProperties("springdoc.custom")
public class SpringDocProperties {

  private String title = "API";
  private String version = "v0.0.1";
  private List<ServerInfo> servers = List.of();

  /**
   * ServerInfo.
   */
  @Data
  public static class ServerInfo {

    private String url;
    private String description;
  }
}
