package com.realestate.service.web.user.request;

import com.realestate.service.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoRequest {

  private String email;
  private String password;
  private int secretCode;

  /** 비밀번호 변경 후 응답객체.
   * */
  public static UserInfoRequest toUserInfoRequest(User user) {

    return UserInfoRequest.builder()
                          .email(user.getEmail())
                          .password(user.getPassword())
                          .secretCode(user.getValidationCode())
                          .build();

  }

}
