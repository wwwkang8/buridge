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

    @PostMapping(value="/signup")
    public ResponseEntity<String> signup(@RequestBody User user){

        System.out.println(user.getEmail());
        System.out.println(user.getPassword());

        return ResponseEntity.ok("efdf");
    }




}
