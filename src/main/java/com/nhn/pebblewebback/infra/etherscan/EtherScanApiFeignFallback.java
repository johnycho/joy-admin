package com.nhn.pebblewebback.infra.etherscan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@Slf4j
public class EtherScanApiFeignFallback implements FallbackFactory<EtherScanApiFeign> {

  @Override
  public EtherScanApiFeign create(final Throwable cause) {
    log.error("etherscan api. message: {}", cause.getMessage(), cause);
    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, cause.getMessage());
  }
}
