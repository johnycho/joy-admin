package com.joy.config.swagger;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@RequiredArgsConstructor
@ConfigurationProperties("springdoc.custom")
public class SpringDocProperties {

  private final String title;
  private final String version;
  private final List<ServerInfo> servers;

  @Data
  public static class ServerInfo {

    private String url;
    private String description;
  }
}
