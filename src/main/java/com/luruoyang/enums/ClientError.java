package com.luruoyang.enums;

public enum ClientError {
  INVALID_DATE("无效的日期"),
  NO_SUCH_DEPT("没有此部门"),
  DEPT_NAME_EXISTS("部门名称重复");

  public String getMsg() {
    return msg;
  }

  private String msg;

  ClientError(String msg) {
    this.msg = msg;
  }
}
