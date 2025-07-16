package com.luruoyang.utils;

import com.luruoyang.enums.LoginEnum;
import lombok.Data;

@Data
public class Result {
  private Integer code;
  private String msg;
  private Object data;

  private Result() {
  }

  private Result(Integer code, String msg, Object data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public static Result success() {
    return new Result(1, "success", null);
  }

  public static Result success(Object data) {
    return new Result(1, "success", data);
  }

  public static Result success(String msg, Object data) {
    return new Result(1, msg, data);
  }

  public static Result success(LoginEnum msg, Object data) {
    return new Result(1, msg.getMsg(), data);
  }

  public static Result fail() {
    return new Result(0, "failed", null);
  }

  public static Result fail(String msg) {
    return new Result(0, msg, msg);
  }
}
