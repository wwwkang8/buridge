package com.realestate.service.user.jwt;

import com.realestate.service.user.dto.LoginUserDto;
import com.realestate.service.user.service.LoginUserAdapter;
import com.realestate.service.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
    com.realestate.service.user.entity.User findUser = userService.findUserByEmail(email);

    // 현재 로그인한 사용자를 LoginUserDto로 생성하여 리턴
    if (findUser.getEmail().equals(email)) {
      LoginUserDto loginUserDto = LoginUserDto.builder()
                                                    .id(findUser.getId())
                                                    .email(findUser.getEmail())
                                                    .password(findUser.getPassword())
                                                    .nickName(findUser.getNickName())
                                                    .status(findUser.getStatus())
                                                    .role(findUser.getRole())
                                                    .build();

      // UserDetailService에서 반환하는 객체는 UserDetail 타입이어야 한다.
      return new LoginUserAdapter(loginUserDto);
    } else {
      throw new UsernameNotFoundException("User not found with email: " + email);
    }
  }
}
