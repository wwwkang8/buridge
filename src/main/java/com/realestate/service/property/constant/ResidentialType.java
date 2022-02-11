package com.realestate.service.property.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 거주 타입을 관리합니다.
 */
@Getter
@RequiredArgsConstructor
public enum ResidentialType {
  HOUSING("주택"),
  OFFICE_HOTEL("오피스텔"),
  APARTMENT("아파트"),
  OFFICE("사무실"),
  STORE("상가"),
  ;

  private final String description;

}
