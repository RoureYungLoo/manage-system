package com.luruoyang.enums;

public enum BusinessErrorEnum {
  DEPT_NOT_EMPTY("禁止删除员工数大于0的部门"),
  ;

  private String errMsg;

  BusinessErrorEnum(String errMsg) {
    this.errMsg = errMsg;
  }

  public String errMsg() {
    return errMsg;
  }
}
