package com.joy.config.swagger;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SpringDocProperties.class)
public class SpringDocConfig {

  @Bean
  public GroupedOpenApi defaultOpenApi(final OpenApiCustomizer openApiCustomizer) {
    return GroupedOpenApi.builder()
                         .group("joyAdminDoc")
                         .pathsToMatch("/**")
                         .addOpenApiCustomizer(openApiCustomizer)
                         .build();
  }

  @Bean
  public OpenApiCustomizer openApiCustomizer(final SpringDocProperties springDocProperties) {
    return openApi ->
        openApi.info(new Info()
                         .title(springDocProperties.getTitle())
                         .version(springDocProperties.getVersion()))
               .servers(springDocProperties.getServers().stream()
                                           .map(serverInfo ->
                                                    new Server().url(serverInfo.getUrl())
                                                                .description(serverInfo.getDescription()))
                                           .toList());
  }
}
