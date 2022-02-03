package com.realestate.service.web.member.response;

import java.time.LocalDateTime;

import com.realestate.service.member.entity.Member;
import lombok.Getter;

@Getter
public class FindMemberResponse {
  private final long id;
  private final String name;
  private final int age;
  private final LocalDateTime createdDateTime;

  /**
   * 생성자.
   */
  public FindMemberResponse(Member member) {
    this.id = member.getId();
    this.name = member.getName();
    this.age = member.getAge();
    this.createdDateTime = member.getCreatedDateTime();
  }

  /**
   * 생성자.
   *
   * @param id                회원 번호
   * @param name              회원 이름
   * @param age               회원 나이
   * @param createdDateTime   회원 가입일
   */
  public FindMemberResponse(long id, String name, int age, LocalDateTime createdDateTime) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.createdDateTime = createdDateTime;
  }
}
