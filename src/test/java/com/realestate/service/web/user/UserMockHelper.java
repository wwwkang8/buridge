package com.realestate.service.web.user;

import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import com.realestate.service.user.dto.UserSignupDto;
import com.realestate.service.user.entity.User;
import com.realestate.service.web.user.request.SignupUserRequest;
import com.realestate.service.web.user.response.SignupUserResponse;
import org.springframework.util.ResourceUtils;

public class UserMockHelper {

  private static final ObjectMapper objectMapper;

  static {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
  }

  final String givenEmail = "test123@naver.com";
  final String givenPassword = "12341234";
  final String givenNickName = "givenNickName";

  /**
   * readValue : Java 오브젝트 --> JSON 변환
   * writeValueAsString : JSON --> Java 오브젝트
   * */
    protected String getSignupRequest() throws IOException {
    return objectMapper.writeValueAsString(
        objectMapper.readValue(
            ResourceUtils.getFile(CLASSPATH_URL_PREFIX +
                "mock/user/signup_user_request.json"), SignupUserRequest.class));
    }

  /**
   * 용도 : User 엔티티를 생성하기 위한 함수
   * 접근제어자 : UserMockHelper는 동일 패키지 내에서 UserRestDoc에서만 호출할 수 있도록 protected로 설정.
   * */
  protected User createUser() {
    return User.builder()
        .email(givenEmail)
        .nickName(givenNickName)
        .status(Status.ACTIVE)
        .role(Role.NORMAL)
        .build();
  }

  /**
   * 용도 : signup 함수 실행시 입력되는 dto 객체를 편하게 생성하기 위해 만듬.
   * 접근제어자 : UserMockHelper는 동일 패키지 내에서 UserRestDoc에서만 호출할 수 있도록 protected로 설정.
   * */
  protected UserSignupDto createUserSignupDto(){
    return UserSignupDto.builder()
                        .email(givenEmail)
                        .password(givenPassword)
                        .nickName(givenNickName)
                        .status(Status.ACTIVE.toString())
                        .role(Role.NORMAL.toString())
                        .build();
  }

  /**
   * 용도 : signup 함수 실행 후, 컨트롤러에서 응답하는 객체를 생성하기 위함
   * 접근제어자 : UserMockHelper는 동일 패키지 내에서 UserRestDoc에서만 호출할 수 있도록 protected로 설정.
   * */
  protected  SignupUserResponse createSignupUserResponse() {

    return new SignupUserResponse(givenEmail, givenNickName, Status.ACTIVE.toString(), Role.NORMAL.toString());

  }


}
