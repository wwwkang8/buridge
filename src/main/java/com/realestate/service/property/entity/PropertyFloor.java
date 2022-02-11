package com.realestate.service.property.entity;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PropertyFloor {
  /**
   * 층.
   */
  private int floor;
  /**
   * 최고층.
   */
  private int topFloor;

  /**
   * 매물 층 정보 생성자.
   */
  public PropertyFloor(int floor,
                       int topFloor) {
    this.floor = floor;
    this.topFloor = topFloor;
  }
}
