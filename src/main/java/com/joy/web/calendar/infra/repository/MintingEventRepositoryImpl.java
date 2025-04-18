package com.joy.web.calendar.infra.repository;

import com.joy.web.calendar.domain.entity.MintingEvent;
import com.joy.web.calendar.domain.repository.MintingEventRepository;
import com.joy.web.calendar.domain.vo.MintingEventInfo;
import com.joy.web.calendar.infra.jpa.MintingEventJpa;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MintingEventRepositoryImpl implements MintingEventRepository {

  private final MintingEventJpa mintingEventJpa;

  @Override
  public void save(final MintingEvent mintingEvent) {
    mintingEventJpa.save(mintingEvent);
  }

  @Override
  public List<MintingEvent> findAll() {
    return mintingEventJpa.findAll();
  }

  @Override
  public List<MintingEvent> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(final LocalDateTime startDate, final LocalDateTime endDate) {
    return mintingEventJpa.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(endDate, startDate);
  }

  @Override
  public List<MintingEventInfo> findMintingEventInfo(final LocalDateTime dateTime) {
    return mintingEventJpa.findMintingEventInfo(dateTime);
  }

  @Override
  public void deleteByUuid(final String uuid) {
    mintingEventJpa.deleteByUuid(uuid);
  }
}
