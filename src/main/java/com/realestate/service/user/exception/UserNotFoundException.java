package com.realestate.service.user.exception;

public class UserNotFoundException extends RuntimeException {

  private static final String MESSAGE = "회원정보가 존재하지 않습니다.[회원정보 : %s]";

  public UserNotFoundException(String email) {
    super(String.format(MESSAGE, email));
  }

  public UserNotFoundException(Long userId) {
    super(String.format(MESSAGE, userId));
  }

}
