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

  String email;
  String password;
  int secretCode;

  public static UserInfoRequest toUserInfoRequest(User user) {

    return UserInfoRequest.builder()
                          .email(user.getEmail())
                          .password(user.getPassword())
                          .secretCode(user.getValidationCode())
                          .build();

  }

}
