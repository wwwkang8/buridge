package com.realestate.service.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.realestate.service.common.entity.BaseDateTimeEntity;
import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class User extends BaseDateTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String email;

  private String password;

  private String nickName;

  private int validationCode;

  @Enumerated(EnumType.STRING)
  private Status status;

  @Enumerated(EnumType.STRING)
  private Role role;

  /**.
   * User 엔티티 생성 메서드
   * @author Kang Jeong Ho
   * @version 1.0
   * */
  public static User createUser(String email,
                                String password,
                                String nickName,
                                Status status,
                                Role role) {
    User user = User.builder()
                    .email(email)
                    .password(password)
                    .nickName(nickName)
                    .status(status)
                    .role(role)
                    .build();

    return user;
  }

  public void updateUserValidationCode(int secretCode) {
    this.validationCode = secretCode;
  }


}
