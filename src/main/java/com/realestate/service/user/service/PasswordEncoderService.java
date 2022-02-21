package com.realestate.service.user.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderService {

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 순환참조 발생 원인 및 해결 방법
   * [원인]
   * UserService.class와 WebSecurityConfig.class 모두 PasswordEncoder 빈을 사용.
   * 빈 생성 흐름상 UserService 빈 생성 --> JwtUserDetailService 빈 생성 --> WebSecurityConfig에서 빈 주입으로 이루어진다.
   * 근데 UserService 빈으로 생성하려면 PasswordEncoder 빈이 생성되어야 하는데, 이걸 WebSecurityConfig에서 생성한다.
   * 그렇다보니 UserService를 생성하려면 WebSecurityConfig가 생성되어야 하고, WebSecurityConfig가 생성되려면 UserService가 생성되어야 하는
   * 순환참조 발생.
   *
   * [해결방법]
   * 설계를 변경한 해결. UserService, WebSecurityConfig 모두 PasswordEncoder를 사용하기 떄문에
   * PasswordEncoder 빈을 생성하는 컴포넌트를 따로 생성하여, 빈을 생성 후 필요한 곳에서 주입받아 사용할 수 있도록 해결.
   *
   * */

}
