package com.joy.web.calendar.presentation.controller;

import com.joy.config.HealthMonitor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthController {

  private final HealthMonitor healthMonitor;

  @GetMapping(value = "/api/health")
  @Operation(summary = "서비스 상태 확인", description = "서비스 상태 확인", responses = @ApiResponse(responseCode = "200"))
  public ResponseEntity<String> checkHealth() {
    return ResponseEntity.ok(healthMonitor.health().getStatus().getCode());
  }
}
