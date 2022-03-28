package com.realestate.service.web.user.response;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import com.realestate.service.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserResponse {

  private Long id;
  private String email;
  private String nickName;
  private Status status;
  private Role role;

  /** 현재 로그인된 사용자정보 응답.
   * 현재 로그인된 사용자의 아이디, 이메일
   * 닉네임, 상태, 권한을 응답한다.
   * */
  public static CurrentUserResponse toCurrentUserResponse(User user) {
    return CurrentUserResponse.builder()
                       .id(user.getId())
                       .email(user.getEmail())
                       .nickName(user.getNickName())
                       .status(user.getStatus())
                       .role(user.getRole())
                       .build();
  }

}
