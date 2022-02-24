package com.realestate.service.user.jwt;

import java.util.ArrayList;
import java.util.Optional;

import com.realestate.service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {

  private final UserService userService;


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    // User 엔티티를 사용하여 이메일로 유저 정보를 조회.
    Optional<com.realestate.service.user.entity.User> findUser =
        Optional.ofNullable(userService.findUserByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException(
                String.format("회원정보가 존재하지 않습니다. 이메일 %s", email)
            )));

    // 스프링에서 제공하는 User 객체 생성.
    if (findUser.get().getEmail().equals(email)) {
      return new User(findUser.get().getEmail(), findUser.get().getPassword(), new ArrayList<>());
    } else {
      throw new UsernameNotFoundException("User not found with email: " + email);
    }
  }
}
