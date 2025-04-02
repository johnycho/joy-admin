package com.nhn.pebblewebback.domain.repository;

import com.nhn.pebblewebback.domain.entity.web.MintingEvent;
import com.nhn.pebblewebback.domain.vo.MintingEventInfo;
import java.time.LocalDateTime;
import java.util.List;

public interface MintingEventRepository {
  void save(final MintingEvent mintingEvent);

  List<MintingEvent> findAll();

  List<MintingEvent> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(final LocalDateTime startDate, final LocalDateTime endDate);

  List<MintingEventInfo> findMintingEventInfo(final LocalDateTime dateTime);

  void deleteByUuid(final String uuid);
}
