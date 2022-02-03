package com.realestate.service.member.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberQueryDto {
  /**
   * 회원 이름.
   */
  private final String name;
  /**
   * 조회 시작일.
   */
  private final LocalDateTime fromDateTime;
  /**
   * 조회 종료일.
   */
  private final LocalDateTime toDateTime;
}
