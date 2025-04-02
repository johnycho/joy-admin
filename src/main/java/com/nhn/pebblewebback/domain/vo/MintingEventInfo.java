package com.nhn.pebblewebback.domain.vo;

import com.nhn.pebblewebback.domain.constant.EventStatus;
import java.time.LocalDateTime;

public interface MintingEventInfo {
  EventStatus getStatus();

  String getUuid();

  String getTitle();

  String getDescription();

  String getContents();

  String getResource();

  LocalDateTime getStartDate();

  LocalDateTime getEndDate();
}
