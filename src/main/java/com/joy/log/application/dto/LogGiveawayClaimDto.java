package com.joy.log.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public class LogGiveawayClaimDto {
  public record LogGiveawayClaimRequest(@Schema(description = "지갑 주소") @NotEmpty @Length(max = 100, message = "suiAddress는 100자 이내로 제한됩니다.") String suiAddress,
                                        @Schema(description = "접속 OS") @NotEmpty @Length(max = 100, message = "os는 100자 이내로 제한됩니다.") String os,
                                        @Schema(description = "접속 환경") @NotEmpty @Length(max = 100, message = "browser는 100자 이내로 제한됩니다.") String browser,
                                        @Schema(description = "보상 클레임 상태") @NotNull @Range(min = 0, max = 5, message = "status는 0~5로 제한됩니다.") Integer status,
                                        @Schema(description = "에러 메시지") @Length(max = 100, message = "errorMessage는 100자 이내로 제한됩니다.") String errorMessage,
                                        @Schema(description = "지급된 보상량") @NotNull @Range(min = 0, max = 5, message = "reward는 0~5로 제한됩니다.") Integer reward) {
  }
}
