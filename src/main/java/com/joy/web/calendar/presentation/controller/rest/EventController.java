package com.joy.web.calendar.presentation.controller.rest;

import com.joy.web.calendar.application.dto.MintingEventDto.MintingEventRestResponse;
import com.joy.web.calendar.application.service.MintingEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@RestControllerEndpoint(id = "event")
@RequiredArgsConstructor
public class EventController {

  private final MintingEventService mintingEventService;

  @GetMapping
  @Operation(summary = "민팅 이벤트 조회", description = "민팅 이벤트 조회", responses = @ApiResponse(responseCode = "200"))
  public ResponseEntity<MintingEventRestResponse> getMintingEventInfo() {
    return ResponseEntity.ok(mintingEventService.getMintingEventInfo());
  }
}
