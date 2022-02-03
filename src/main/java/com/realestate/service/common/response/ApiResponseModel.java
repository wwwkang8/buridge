package com.realestate.service.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponseModel {
  private final Boolean success;
  private final String message;
  private final Object data;

  /**
    * 생성자.
    */
  @Builder
  public ApiResponseModel(Boolean success, String message, Object data) {
    this.success = success;
    this.message = message;
    this.data = data;
  }

}
