package com.joy.config.security;

import com.joy.config.JoyProperties;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@EnableConfigurationProperties(JoyProperties.class)
@RequiredArgsConstructor
public class SecurityConfig {

  private final JoyProperties joyProperties;

  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http, final Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer) throws Exception {
    return http.authorizeHttpRequests(configurer ->
                                          configurer.requestMatchers("/", "/home", "/student/**", "/blog/**")
                                                    .hasAnyAuthority("ROLE_ADMIN", "OIDC_USER")
                                                    .requestMatchers("/api/**")
                                                    .authenticated()
                                                    .anyRequest()
                                                    .permitAll())
               .formLogin(configurer -> configurer.loginPage("/auth/login")
                                                  .loginProcessingUrl("/login/process")
                                                  .usernameParameter("userId")
                                                  .passwordParameter("userPassword")
                                                  .successHandler(new CustomLoginSuccessHandler())
                                                  .failureHandler(new CustomLoginFailureHandler())
               )
               .oauth2Login(oauth -> oauth.defaultSuccessUrl("/", true))
               .logout(configurer -> configurer.logoutUrl("/auth/logout")
                                               .logoutSuccessUrl("/auth/login")
                                               .invalidateHttpSession(true)
                                               .deleteCookies("SESSION"))
               .exceptionHandling(configurer -> configurer.accessDeniedPage("/error/error"))
               .csrf(configurer -> configurer.ignoringRequestMatchers("/api/**"))
               .cors(corsCustomizer)
               .build();
  }

  @Bean
  public UserDetailsService userDetailsService(final DataSource joyWebDataSource) {
    return new JdbcUserDetailsManager(joyWebDataSource);
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer() {
    return cors -> {
      final CorsConfiguration configuration = new CorsConfiguration();
      configuration.setAllowedOrigins(joyProperties.getCors().getOrigins());
      configuration.setAllowedHeaders(List.of("*"));
      configuration.setAllowedMethods(List.of("*"));

      final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);

      cors.configurationSource(source);
    };
  }
}
