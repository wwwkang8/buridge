package com.realestate.service.user.service;

import java.util.ArrayList;

import com.realestate.service.user.dto.LoginUserDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;

@Getter
@Slf4j
public class LoginUserAdapter extends User {

  /**
   * 참고문서 : https://jaimemin.tistory.com/2078?category=1068792
   * User 클래스(org.springframework.security.core.userdetails.User) 상속받는 이유
   * UserDetailService에서 반환하는 객체는 UserDetail 타입이어야 하기 때문에.
   * 그리고 기본적인 UserDetail의 User 객체는 이메일, 비밀번호만 조회가 가능하기 때문에
   * User 엔티티 정보를 추가하여 아이디, 이메일, 닉네임도 조회할 수 있도록 함.
   * */

  private LoginUserDto loginUserDto;

  public LoginUserAdapter(LoginUserDto loginUserDto) {
    super(loginUserDto.getEmail(), loginUserDto.getPassword(), new ArrayList<>());
    this.loginUserDto = loginUserDto;
  }

}
