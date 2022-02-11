package com.realestate.service.property.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 매물 구조 타입을 관리합니다.
 */
@Getter
@RequiredArgsConstructor
public enum StructureType {
  ONE_ROOM("원룸"),
  TWO_ROOM("방 2개"),
  TREE_ROOM("방 3개"),
  MORE_THAN_FOUR_ROOM("방 4개 이상"),
  DUPLEX("복층"),
  ;

  private final String description;
}
