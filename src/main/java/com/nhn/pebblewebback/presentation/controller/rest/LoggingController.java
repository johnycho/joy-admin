package com.nhn.pebblewebback.presentation.controller.rest;

import com.nhn.pebblewebback.application.dto.LogGiveawayClaimDto.LogGiveawayClaimRequest;
import com.nhn.pebblewebback.application.dto.LogWalletMintingDto.LogWalletMintingRequest;
import com.nhn.pebblewebback.application.service.LoggingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@RestControllerEndpoint(id = "logging")
@RequiredArgsConstructor
public class LoggingController {

  private final LoggingService loggingService;

  @PostMapping
  @Operation(summary = "Community NFT 이벤트 유저 액션 로깅", description = "Community NFT 이벤트 유저 액션에 대한 결과를 로깅 처리", responses = @ApiResponse(responseCode = "200"))
  public ResponseEntity<Boolean> logging(@Validated @RequestBody final LogWalletMintingRequest request) {
    return ResponseEntity.ok(loggingService.addLogWalletMinting(request));
  }

  @PostMapping("/giveaway")
  @Operation(summary = "Sui Giveaway 이벤트 유저 액션 로깅", description = "Sui Giveaway 이벤트 유저 액션에 대한 결과를 로깅 처리", responses = @ApiResponse(responseCode = "200"))
  public ResponseEntity<Boolean> logging(@Validated @RequestBody final LogGiveawayClaimRequest request) {
    return ResponseEntity.ok(loggingService.addLogGiveawayClaim(request));
  }
}
