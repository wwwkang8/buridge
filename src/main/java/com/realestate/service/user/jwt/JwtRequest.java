package com.realestate.service.user.jwt;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest  {

  @NotBlank(message = "이메일 입력은 필수입니다.")
  @Email(message = "이메일 형식이 올바르지 않습니다.")
  private String email;

  @NotBlank(message = "비밀번호 입력은 필수입니다.")
  @Length(min = 8, max = 20, message = "8~20자리 비밀번호를 입력해주세요.")
  private String password;


}
