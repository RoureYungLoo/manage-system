package com.luruoyang.enums;

public enum LoginEnum {
  OK("登录成功"),
  ERR_NOT_MATCH("用户名或密码错误");
  private String msg;

  public String getMsg() {
    return msg;
  }

  LoginEnum(String msg) {
    this.msg = msg;
  }
}
