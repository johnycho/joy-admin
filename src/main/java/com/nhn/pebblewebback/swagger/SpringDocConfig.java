package com.nhn.pebblewebback.swagger;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SpringDocProperties.class)
@RequiredArgsConstructor
class SpringDocConfig {

  private final SpringDocProperties springDocProperties;

  @Bean
  OpenApiCustomizer commonOpenApiCustomizer() {
    return openApi -> openApi.servers(servers())
                             .info(info());
  }

  private List<Server> servers() {
    return springDocProperties.getServers()
                              .stream()
                              .map(s -> new Server().url(s.getUrl()).description(s.getDescription()))
                              .toList();
  }

  private Info info() {
    return new Info()
        .title(springDocProperties.getTitle())
        .version(springDocProperties.getVersion());
  }
}
