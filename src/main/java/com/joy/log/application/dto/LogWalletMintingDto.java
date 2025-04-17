package com.joy.log.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public class LogWalletMintingDto {
  public record LogWalletMintingRequest(@Schema(description = "지갑 주소") @NotEmpty @Length(max = 100, message = "walletAddress는 100자 이내로 제한됩니다.") String walletAddress,
                                        @Schema(description = "접속 OS") @NotEmpty @Length(max = 100, message = "os는 100자 이내로 제한됩니다.") String os,
                                        @Schema(description = "접속 환경") @NotEmpty @Length(max = 100, message = "browser는 100자 이내로 제한됩니다.") String browser,
                                        @Schema(description = "민팅 상태") @NotNull @Range(min = 0, max = 2, message = "status는 0~2로 제한됩니다.") Integer status,
                                        @Schema(description = "에러 메시지") @Length(max = 100, message = "errorMessage는 100자 이내로 제한됩니다.") String errorMessage,
                                        @Schema(description = "토큰 ID") @Length(max = 50, message = "tokenId는 50자 이내로 제한됩니다.") String tokenId,
                                        @Schema(description = "이벤트 UUID") @NotEmpty @Length(max = 30, message = "eventUuid는 30자 이내로 제한됩니다.") String eventUuid) {
  }
}
