package com.realestate.service.web.user;

import com.realestate.service.common.response.ApiResponseModel;
import com.realestate.service.user.dto.UserSignupDto;
import com.realestate.service.user.entity.User;
import com.realestate.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/")
public class UserRestController {

  @Autowired
  UserService userService;

  /**.
   * 회원가입 : 이메일, 비밀번호를 입력하면 회원가입
   * @author Kang Jeong Ho
   * @version 1.0
   * */
  @PostMapping(value = "/signup")
  public void signup(@RequestBody UserSignupDto userSignupDto) {

    userService.signup(userSignupDto);

  }

  /**.
   * 인증코드 발행 및 이메일 전송 : 비밀번호 변경을 위한 인증코드 발행 및 이메일 전송
   * @author Kang Jeong Ho
   * @version 1.0
   * */
  @PostMapping(value = "/password/validation")
  public void sendValidationCode(@RequestBody UserSignupDto userSignupDto) {

    userService.sendValidationCode(userSignupDto);

  }




}
