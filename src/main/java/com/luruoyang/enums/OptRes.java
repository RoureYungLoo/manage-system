package com.luruoyang.enums;

public enum OptRes {

  success(1),
  failed(2);
  private Integer code;

  public Integer getCode() {
    return code;
  }

  OptRes(Integer code) {
    this.code = code;
  }
}
