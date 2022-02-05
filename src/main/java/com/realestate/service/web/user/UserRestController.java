package com.realestate.service.web.user;

import com.realestate.service.user.entity.User;
import com.realestate.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
  public ResponseEntity<String> signup(@RequestBody User user) {
    //Long id = userService.signup(user);

    return ResponseEntity.ok("efdf");

  }




}
