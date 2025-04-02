package com.nhn.pebblewebback.application.service;

import com.nhn.pebblewebback.application.dto.MintingEventDto.MintingEventMvcRequest;
import com.nhn.pebblewebback.application.dto.MintingEventDto.MintingEventMvcResponse;
import com.nhn.pebblewebback.application.dto.MintingEventDto.MintingEventRestResponse;
import com.nhn.pebblewebback.domain.entity.web.MintingEvent;
import com.nhn.pebblewebback.domain.repository.MintingEventRepository;
import com.nhn.pebblewebback.exception.constant.ExceptionInfo;
import com.nhn.pebblewebback.presentation.mapper.MintingEventEntityMapper;
import com.nhn.pebblewebback.presentation.mapper.MintingEventInfoDtoMapper;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 민팅 이벤트 서비스.
 */
@Service
@RequiredArgsConstructor
public class MintingEventService {

  private final MintingEventEntityMapper mintingEventEntityMapper;
  private final MintingEventInfoDtoMapper mintingEventInfoDtoMapper;
  private final MintingEventRepository mintingEventRepository;

  @Transactional
  public void addMintingEvent(final MintingEventMvcRequest request) {
    validateRequest(request);
    mintingEventRepository.save(mintingEventEntityMapper.toEntity(request));
  }

  @Transactional
  public void deleteMintingEvent(final String uuid) {
    mintingEventRepository.deleteByUuid(uuid);
  }

  @Transactional(readOnly = true)
  public List<MintingEventMvcResponse> getAllEvents() {
    return mintingEventRepository.findAll()
                                 .stream()
                                 .map(MintingEventMvcResponse::of)
                                 .toList();
  }

  @Transactional(readOnly = true)
  public MintingEventRestResponse getMintingEventInfo() {
    return mintingEventInfoDtoMapper.toDto(mintingEventRepository.findMintingEventInfo(LocalDateTime.now())
                                                                 .stream()
                                                                 .findFirst()
                                                                 .orElseThrow(ExceptionInfo.NOT_FOUND_MINTING_EVENT::exception));
  }

  private void validateRequest(final MintingEventMvcRequest request) {
    if ((request.startDate().isBefore(LocalDateTime.now())) || (request.startDate().isAfter(request.endDate()))) {
      throw ExceptionInfo.INVALID_MINTING_EVENT_PERIOD.exception();
    }
    checkMintingEventPeriodOverlapped(request);
  }

  private void checkMintingEventPeriodOverlapped(final MintingEventMvcRequest request) {
    final List<MintingEvent> mintingEvents = mintingEventRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(request.startDate(), request.endDate());
    if (!mintingEvents.isEmpty()) {
      throw ExceptionInfo.NOT_ALLOWED_MINTING_EVENT_PERIOD_OVERLAPPED.exception();
    }
  }
}
