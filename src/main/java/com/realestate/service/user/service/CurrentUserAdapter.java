package com.realestate.service.user.service;

import java.util.ArrayList;

import lombok.Getter;
import org.springframework.security.core.userdetails.User;

@Getter
public class CurrentUserAdapter extends User {

  /**
   * 참고문서 : https://jaimemin.tistory.com/2078?category=1068792
   * User 클래스(org.springframework.security.core.userdetails.User) 상속받는 이유
   * UserDetailService에서 반환하는 객체는 UserDetail 타입이어야 하기 때문에.
   * 그리고 기본적인 UserDetail의 User 객체는 이메일, 비밀번호만 조회가 가능하기 때문에
   * User 엔티티 정보를 추가하여 아이디, 이메일, 닉네임도 조회할 수 있도록 함.
   * */

  private com.realestate.service.user.entity.User currentUser;

  public CurrentUserAdapter(com.realestate.service.user.entity.User user) {
    super(user.getEmail(), user.getPassword(), new ArrayList<>());
    this.currentUser = user;
  }

}
