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
public class SignupUserResponse {

  private String email;
  private String nickName;
  private String status;
  private String role;

  /**.
   * 회원가입 응답 클래스 : 회원가입 완료 후 save된 이메일, 닉네임, 상태, 권한을 응답하여
   * 정상적으로 가입되었는지를 보여줌.
   * */
  public static SignupUserResponse toSignupUserResponse(User user) {

    return SignupUserResponse.builder()
                             .email(user.getEmail())
                             .nickName(user.getNickName())
                             .status(user.getStatus().toString())
                             .role(user.getRole().toString())
                             .build();

  }




}
