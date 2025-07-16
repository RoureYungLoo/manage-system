package com.luruoyang.exception;

import com.luruoyang.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MyException.class)
  public Result myException(MyException e) {
    e.printStackTrace();
    return Result.fail(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public Result globalException(Exception e) {
    e.printStackTrace();
    return Result.fail(e.getMessage());
  }

  @ExceptionHandler(SystemException.class)
  public Result SystemException(SystemException se) {
    log.warn("服务端异常: {}", se.getMessage());
    se.printStackTrace();
    return Result.fail(se.getMessage());
  }

  @ExceptionHandler(ClientSideException.class)
  public Result ClientSideException(ClientSideException ce) {
    log.warn("客户端异常: {}", ce.getMessage());
    ce.printStackTrace();
    return Result.fail(ce.getMessage());
  }

  @ExceptionHandler(LoginException.class)
  public Result LoginException(LoginException le) {
    log.warn("登录异常:{}", le.getMessage());
    return Result.fail(le.getMessage());
  }
}
