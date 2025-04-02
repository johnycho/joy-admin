package com.nhn.pebblewebback.exception.dto;

import com.nhn.pebblewebback.exception.vo.CustomException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

/**
 * ExceptionResponse.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

  private Integer status;
  private String message;

  public static ResponseEntity<ExceptionResponse> of(final CustomException exception) {
    return ResponseEntity.status(exception.getStatusCode())
                         .body(ExceptionResponse.builder()
                                                .status(exception.getStatusCode())
                                                .message(exception.getMessage())
                                                .build());
  }
}
