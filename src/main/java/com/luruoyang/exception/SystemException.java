package com.luruoyang.exception;

public class SystemException extends RuntimeException{
  public SystemException() {
  }

  public SystemException(String message) {
    super(message);
  }
}
