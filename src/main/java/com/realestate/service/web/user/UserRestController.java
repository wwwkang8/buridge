package com.realestate.service.web.user;

import javax.validation.Valid;

import com.realestate.service.user.dto.UserInfoDto;
import com.realestate.service.user.dto.UserSignupDto;
import com.realestate.service.user.entity.User;
import com.realestate.service.user.jwt.JwtRequest;
import com.realestate.service.user.jwt.JwtResponse;
import com.realestate.service.user.jwt.JwtTokenUtil;
import com.realestate.service.user.jwt.JwtUserDetailService;
import com.realestate.service.user.service.CurrentUser;
import com.realestate.service.user.service.UserService;
import com.realestate.service.web.user.response.CurrentUserResponse;
import com.realestate.service.web.user.response.SignupUserResponse;
import com.realestate.service.web.user.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserRestController {

  private final UserService userService;

  private final JwtTokenUtil jwtTokenUtil;

  private final JwtUserDetailService jwtUserDetailService;

  private final AuthenticationManager authenticationManager;


  /**.
   * 회원가입 : 이메일, 비밀번호를 입력하면 회원가입
   * */
  @PostMapping(value = "/signup")
  public SignupUserResponse signup(@RequestBody @Valid UserSignupDto userSignupDto) {

    User savedUser = userService.signup(userSignupDto);

    return SignupUserResponse.toSignupUserResponse(savedUser);

  }

  /**.
   * 인증코드 발행 및 이메일 전송 : 비밀번호 변경을 위한 인증코드 발행 및 이메일 전송
   * */
  @PostMapping(value = "/password/validation")
  public void sendValidationCode(@RequestBody @Valid UserInfoDto userInfoDto) {

    userService.sendValidationCode(userInfoDto);

  }

  /**
   * 비밀번호 변경 : 비밀번호를 변경한다.
   * */
  @PostMapping(value = "/password/change")
  public UserInfoResponse changePassword(@RequestBody @Valid UserInfoDto userInfoDto) {

    User user = userService.changePassword(userInfoDto);

    return UserInfoResponse.toUserInfoResponse(user);
  }


  /**.
   * 로그인 : JWT 토큰을 발행하여 리턴한다.
   * */
  @PostMapping(value = "/authenticate")
  public JwtResponse createAuthenticationToken(@RequestBody @Valid JwtRequest jwtRequest)
      throws Exception {

    authenticate(jwtRequest.getEmail(), jwtRequest.getPassword());

    UserDetails userDetails = jwtUserDetailService.loadUserByUsername(jwtRequest.getEmail());

    final String token = jwtTokenUtil.generateToken(userDetails);

    return new JwtResponse(token);
  }

  /** 입력받은 이메일, 비밀번호 검증
   * 해당 메서드를 JwtTokenUtil에서 호출하는 것으로 하였으나
   * 순환참조가 발생하여 Controller로 뺐다.
   * */
  public void authenticate(String email, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }


  /** LoginUser 어노테이션.
   * 로그인한 사용자 정보를 불러오는 어노테이션
   * */
  @GetMapping(value = "/currentUser")
  public CurrentUserResponse loginUser(@CurrentUser User currentUser) {

    log.info("currentUser ID : " + currentUser.getId());
    log.info("currentUser Email : " + currentUser.getEmail());
    log.info("currentUser NickName : " + currentUser.getNickName());

    return CurrentUserResponse.toCurrentUserResponse(currentUser);
  }


}
