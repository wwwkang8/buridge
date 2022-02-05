package com.realestate.service.user.entity;

import javax.persistence.*;

import com.realestate.service.common.entity.BaseDateTimeEntity;
import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import lombok.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseDateTimeEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="user_id")
  private Long id;

  private String email;

  private String password;

  private String nickName;

  @Enumerated(EnumType.STRING)
  private Status status;

  @Enumerated(EnumType.STRING)
  private Role role;


}
