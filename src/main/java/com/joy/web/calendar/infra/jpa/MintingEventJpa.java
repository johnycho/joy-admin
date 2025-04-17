package com.joy.web.calendar.infra.jpa;

import com.joy.web.calendar.domain.entity.MintingEvent;
import com.joy.web.calendar.domain.vo.MintingEventInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MintingEventJpa extends JpaRepository<MintingEvent, Long> {

  Optional<MintingEvent> findByUuid(final String uuid);

  List<MintingEvent> findAllByEndDateGreaterThanEqual(final LocalDateTime dateTime);

  List<MintingEvent> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(final LocalDateTime endDate, final LocalDateTime startDate);

  Optional<MintingEvent> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(final LocalDateTime dateTime1, final LocalDateTime dateTime2);

  default Optional<MintingEvent> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(final LocalDateTime dateTime) {
    return findByStartDateLessThanEqualAndEndDateGreaterThanEqual(dateTime, dateTime);
  }

  @Query(
      "SELECT 'ACTIVE' AS status, me.uuid AS uuid, me.title AS title, me.description AS description, me.contents AS contents, me.resource AS resource, me.startDate AS startDate, me.endDate AS endDate FROM MintingEvent me WHERE startDate <= :dateTime AND me.endDate >= :dateTime "
      + "UNION "
      + "SELECT 'UPCOMING' AS status, me.uuid AS uuid, me.title AS title, me.description AS description, me.contents AS contents, me.resource AS resource, me.startDate AS startDate, me.endDate AS endDate FROM MintingEvent me WHERE startDate > :dateTime ORDER BY me.startDate LIMIT 1 "
      + "UNION "
      + "SELECT 'ENDED' AS status, me.uuid AS uuid, me.title AS title, me.description AS description, me.contents AS contents, me.resource AS resource, me.startDate AS startDate, me.endDate AS endDate FROM MintingEvent me WHERE endDate < :dateTime ORDER BY me.endDate DESC LIMIT 1")
  List<MintingEventInfo> findMintingEventInfo(final LocalDateTime dateTime);

  void deleteByUuid(final String uuid);
}
