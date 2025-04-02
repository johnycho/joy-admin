package com.nhn.pebblewebback.application.dto;

import com.nhn.pebblewebback.application.util.DateTimeUtils;
import com.nhn.pebblewebback.domain.constant.EventStatus;
import com.nhn.pebblewebback.domain.entity.web.MintingEvent;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class MintingEventDto {

  public static final MintingEventMvcRequest EMPTY_MINTING_EVENT_REQUEST = new MintingEventMvcRequest(null, null, null, null, null, null);

  public record MintingEventMvcRequest(@NotBlank(message = "이벤트 타이틀을 입력하세요.")
                                       String title,
                                       @NotBlank(message = "이벤트 설명을 입력하세요.")
                                       String description,
                                       @NotBlank(message = "이벤트 내용을 입력하세요.")
                                       String contents,
                                       @NotBlank(message = "리소스 URI를 입력하세요.")
                                       String resource,
                                       @NotNull(message = "이벤트 시작일을 입력하세요.")
                                       LocalDateTime startDate,
                                       @NotNull(message = "이벤트 종료일을 입력하세요.")
                                       LocalDateTime endDate) {
  }

  public record MintingEventMvcResponse(String title,
                                        String description,
                                        String contents,
                                        String resource,
                                        String uuid,
                                        LocalDateTime utcStartDate,
                                        LocalDateTime utcEndDate,
                                        LocalDateTime kstStartDate,
                                        LocalDateTime kstEndDate) {

    public static MintingEventMvcResponse of(final MintingEvent mintingEvent) {
      return new MintingEventMvcResponse(mintingEvent.getTitle(),
                                         mintingEvent.getDescription(),
                                         mintingEvent.getContents(),
                                         mintingEvent.getResource(),
                                         mintingEvent.getUuid(),
                                         mintingEvent.getStartDate(),
                                         mintingEvent.getEndDate(),
                                         DateTimeUtils.convertToKstDateTime(mintingEvent.getStartDate()),
                                         DateTimeUtils.convertToKstDateTime(mintingEvent.getEndDate()));
    }
  }

  public record MintingEventRestResponse(@Schema(description = "이벤트 UUID") String uuid,
                                         @Schema(description = "이벤트 상태") EventStatus status,
                                         @Schema(description = "이벤트 타이틀") String title,
                                         @Schema(description = "이벤트 설명") String description,
                                         @Schema(description = "이벤트 내용") String contents,
                                         @Schema(description = "리소스 URI") String resource,
                                         @Schema(description = "이벤트 시작일") LocalDateTime startDate,
                                         @Schema(description = "이벤트 종료일") LocalDateTime endDate) {
  }
}
