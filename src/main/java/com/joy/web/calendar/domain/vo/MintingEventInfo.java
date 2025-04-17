package com.joy.web.calendar.domain.vo;

import com.joy.web.calendar.domain.constant.EventStatus;
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
