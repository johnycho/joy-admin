package com.joy.config.swagger;

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

  private String title = "Joy Admin API";
  private String version = "v1.0.0";
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
