package com.realestate.service.common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
  private final String message;
  private final HttpStatus status;

  /**
   * 에러 응답 생성자.
   */
  @Builder
  public ErrorResponse(String message, HttpStatus status) {
    this.message = message;
    this.status = status;
  }
}
