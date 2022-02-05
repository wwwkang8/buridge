package com.realestate.service.user.service;

import com.realestate.service.user.entity.User;
import com.realestate.service.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

//  public Long signup(User user){
//
//  }

}
