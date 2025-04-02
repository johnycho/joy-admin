package com.nhn.pebblemanegement.config;

import com.nhn.pebblewebback.application.filter.ContentLoggingFilter;
import org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@ManagementContextConfiguration
public class ManagementFilterConfiguration {

  @Bean
  public FilterRegistrationBean<ContentLoggingFilter> managementFilter() {
    final FilterRegistrationBean<ContentLoggingFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new ContentLoggingFilter());
    registrationBean.setEnabled(true);
    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }
}
