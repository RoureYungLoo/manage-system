package com.luruoyang.exception;

import com.luruoyang.enums.BusinessErrorEnum;
import com.luruoyang.enums.ClientError;

public class BusinessException extends RuntimeException {
  public BusinessException(BusinessErrorEnum businessError) {
    super(businessError.errMsg());
  }
}
