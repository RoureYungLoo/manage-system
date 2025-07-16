package com.luruoyang.exception;

public class SalaryTooLowException extends RuntimeException {
  public SalaryTooLowException(String message) {
    super(message);
  }
}
