package com.luruoyang.exception;

import com.luruoyang.enums.ClientError;

public class ClientSideException extends RuntimeException {
  public ClientSideException() {
  }

  public ClientSideException(String message) {
    super(message);
  }

  public ClientSideException(ClientError errMsg) {
    super(errMsg.getMsg());
  }
}
