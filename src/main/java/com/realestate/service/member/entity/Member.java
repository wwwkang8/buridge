package com.realestate.service.member.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.realestate.service.common.entity.BaseDateTimeEntity;
import com.realestate.service.member.constant.GenderType;
import com.realestate.service.member.converter.GenderTypeConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * TODO: 테스트용 엔티티 입니다. 추후 제거해주세요.
 */
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "members")
public class Member extends BaseDateTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private int age;

  @Convert(converter = GenderTypeConverter.class)
  private GenderType gender;

  /**
   * 생성자.
   */
  public Member(String name,
                int age,
                GenderType gender) {
    this.name = name;
    this.age = age;
    this.gender = gender;
  }
}
