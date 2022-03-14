package com.realestate.service.web.user.response;

import com.realestate.service.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

  String email;
  String result;

  /**
   * 비밀번호 변경 응답 클래스 : 비밀번호 변경 후 완료 메시지 응답.
   * */
  public static UserInfoResponse toUserInfoResponse(User user) {
    return UserInfoResponse.builder()
                          .email(user.getEmail())
                          .result(String.format("%s 회원님의 비밀번호 변경완료.", user.getEmail()))
                          .build();
  }

}
