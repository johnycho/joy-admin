package com.nhn.pebblewebback.infra.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public enum EtherScanApiModuleType {
  ACCOUNT("account"),
  CONTRACT("contract"),
  TRANSACTION("transaction"),
  BLOCK("block"),
  STATS("stats"),
  LOGS("logs"),
  PROXY("proxy"),
  TOKEN("token"),
  GAS_TRACKER("gastracker");

  private final String code;
}
