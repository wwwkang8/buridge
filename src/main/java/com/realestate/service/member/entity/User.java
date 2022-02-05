package com.realestate.service.member.entity;

import javax.persistence.*;

import com.realestate.service.common.entity.BaseDateTimeEntity;
import com.realestate.service.member.constant.GenderType;
import com.realestate.service.member.constant.Role;
import com.realestate.service.member.constant.Status;
import com.realestate.service.member.converter.GenderTypeConverter;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
