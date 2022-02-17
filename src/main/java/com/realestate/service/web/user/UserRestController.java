package com.realestate.service.web.user;

import javax.validation.Valid;

import com.realestate.service.user.dto.UserEmailDto;
import com.realestate.service.user.dto.UserSignupDto;
import com.realestate.service.user.entity.User;
import com.realestate.service.user.service.UserService;
import com.realestate.service.web.user.response.SignupUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users/")
public class UserRestController {

  @Autowired
  UserService userService;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

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
  public void sendValidationCode(@RequestBody @Valid UserEmailDto userEmailDto) {

    userService.sendValidationCode(userEmailDto);

  }




}
