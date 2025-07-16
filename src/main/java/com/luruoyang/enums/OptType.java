package com.luruoyang.enums;

public enum OptType {
  insert(1), update(2), delete(3), select(4);
  private Integer code;

  public Integer getCode() {
    return code;
  }

  OptType(Integer code) {
    this.code = code;
  }
}
