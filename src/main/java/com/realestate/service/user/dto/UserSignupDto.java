package com.realestate.service.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  @NotBlank
  private String email;
  private String password;
  private String nickName;
  private String status;
  private String Role;


}
