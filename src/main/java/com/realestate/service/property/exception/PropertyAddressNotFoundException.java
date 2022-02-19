package com.realestate.service.property.exception;

public class PropertyAddressNotFoundException extends RuntimeException {
  private static final String MESSAGE = "매물 주소 정보가 존재하지 않습니다.[매물 번호 : %d]";

  public PropertyAddressNotFoundException(Long propertyId) {
    super(String.format(MESSAGE, propertyId));
  }
}
