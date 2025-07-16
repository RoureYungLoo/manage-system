package com.luruoyang.service;


import com.luruoyang.model.pojo.LogEmp;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface LogEmpService {

  /* 子事务需要重新开启一个事物 */
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  boolean saveLog(LogEmp logEmp);
}
