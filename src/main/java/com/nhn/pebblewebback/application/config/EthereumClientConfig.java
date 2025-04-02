package com.nhn.pebblewebback.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
@ConditionalOnProperty(value = "ethereum.network-url")
public class EthereumClientConfig {

  @Value("${ethereum.network-url}")
  private String networkUrl;

  @Bean
  public Web3j web3j() {
    return Web3j.build(new HttpService(networkUrl));
  }
}
