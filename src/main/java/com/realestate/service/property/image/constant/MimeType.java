package com.realestate.service.property.image.constant;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 화이트 리스트 확장자 타입을 관리합니다.
 */
@Getter
@RequiredArgsConstructor
public enum MimeType {
  JPEG("image/jpeg"),
  JPG("image/jpg"),
  PNG("image/png"),
  ;

  private final String value;

  /**
   * ofValue.
   */
  public static MimeType ofValue(String value) {
    return Arrays.stream(MimeType.values())
        .filter(v -> v.getValue().equals(value))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("%s(은)는 제한된 확장자 입니다.", value)));
  }

}
