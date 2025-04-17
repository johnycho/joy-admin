package com.joy.web.calendar.presentation.controller.rest;

import com.joy.web.calendar.application.dto.SignatureDto.SignatureResponse;
import com.joy.web.calendar.application.service.SignatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@RestControllerEndpoint(id = "signature")
@RequiredArgsConstructor
public class SignatureController {
  private final SignatureService signatureService;

  @PostMapping(value = { "/{type}" })
  @Operation(summary = "메시지 서명", description = "Pre Sign", responses = @ApiResponse(responseCode = "200"))
  public ResponseEntity<SignatureResponse> sign(@Schema(description = "이벤트 타입(cn: CommunityNFT, ga: Giveaway)") @PathVariable String type,
                                                @Schema(description = "Hex String 메시지") @RequestBody final String message)
      throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
    return ResponseEntity.ok(signatureService.sign(type, message));
  }
}
