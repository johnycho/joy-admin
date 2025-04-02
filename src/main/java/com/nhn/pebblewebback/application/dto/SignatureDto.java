package com.nhn.pebblewebback.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class SignatureDto {
  public record SignatureResponse(@Schema(description = "메시지") String message,
                                  @Schema(description = "서명") String sign) {
    public static SignatureResponse of(final String message, final String sign) {
      return new SignatureResponse(message, sign);
    }
  }
}
