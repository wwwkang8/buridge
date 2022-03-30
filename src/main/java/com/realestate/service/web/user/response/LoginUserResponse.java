package com.realestate.service.web.user.response;

import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import com.realestate.service.user.dto.LoginUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserResponse {

  private Long id;
  private String email;
  private String nickName;
  private Status status;
  private Role role;

  /** 현재 로그인된 사용자정보 응답.
   * 현재 로그인된 사용자의 아이디, 이메일
   * 닉네임, 상태, 권한을 응답한다.
   * */
  public static LoginUserResponse toLoginUserResponse(LoginUserDto loginUserDto) {
    return LoginUserResponse.builder()
                       .id(loginUserDto.getId())
                       .email(loginUserDto.getEmail())
                       .nickName(loginUserDto.getNickName())
                       .status(loginUserDto.getStatus())
                       .role(loginUserDto.getRole())
                       .build();
  }

}
