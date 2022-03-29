package com.realestate.service.user.dto;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserDto {

  private Long id;

  private String email;

  private String password;

  private String nickName;

  private Status status;

  private Role role;


}
