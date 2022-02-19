package com.realestate.service.property.exception;

public class PropertyNotFoundException extends RuntimeException {
  private static final String MESSAGE = "매물 정보가 존재하지 않습니다.[매물 번호 : %d]";

  public PropertyNotFoundException(Long propertyId) {
    super(String.format(MESSAGE, propertyId));
  }
}
