package com.realestate.service.web.user;

import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import com.realestate.service.user.dto.UserSignupDto;
import com.realestate.service.user.entity.User;
import com.realestate.service.user.jwt.JwtRequest;
import com.realestate.service.user.jwt.JwtResponse;
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
  final String givenToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MTIzQG5hdmVyLmNvbSIsImV4cCI6MTY0NTYxNTYzMywiaWF0IjoxNjQ1NTI5MjMzfQ.r8fr68_kmyb_nWYenjNUbejLdI6ZWV4n5HxfXT6uZMk6dhPysMYBwGfXDTHYBYY-qfkkqYi6fgbjvvhVYwYajw";

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
   * readValue : Java 오브젝트 --> JSON 변환
   * writeValueAsString : JSON --> Java 오브젝트
   * */
  protected String getJwtRequest() throws IOException {
    return objectMapper.writeValueAsString(
        objectMapper.readValue(
            ResourceUtils.getFile(CLASSPATH_URL_PREFIX +
                "mock/user/jwt_request.json"), JwtRequest.class));
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

  /**
   * 용도 : JWT 토큰 발행을 위한 사용자 이메일, 비밀번호 Request 생성
   * */
  protected JwtRequest createJwtRequest() {
    return JwtRequest.builder()
                     .email(givenEmail)
                     .password(givenPassword)
                     .build();
  }

  /**
   * 용도 : JWT 토큰 전송을 위한 Response 생성
   * */
  protected JwtResponse createJwtResponse() {
    return JwtResponse.builder()
        .jwtToken(givenToken)
        .build();
  }




}
