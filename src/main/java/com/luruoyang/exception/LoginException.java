package com.luruoyang.exception;

import com.luruoyang.enums.LoginEnum;

public class LoginException extends RuntimeException {
  public LoginException(String message) {
    super(message);
  }
  public LoginException(LoginEnum loginEnum) {
    super(loginEnum.getMsg());
  }

}
