package com.realestate.service.member.constant;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum GenderType {
  MALE("남자", "0"),
  FEMALE("여자", "1")
  ;
  private final String desc;
  private final String value;

  GenderType(String desc, String value) {
    this.desc = desc;
    this.value = value;
  }

  /**
   * ofValue.
   */
  public static GenderType ofValue(String value) {
    return Arrays.stream(GenderType.values())
            .filter(v -> v.getValue().equals(value))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(
                    String.format("%s(은)는 DB에 NULL 혹은 Empty 로 저장되어 있습니다.", value)));
  }
}
