package com.luruoyang.service.impl;

import com.luruoyang.mapper.LogEmpMapper;
import com.luruoyang.model.pojo.LogEmp;
import com.luruoyang.service.LogEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogEmpServiceImpl implements LogEmpService {


  @Autowired
  private LogEmpMapper logEmpMapper;

  @Override
  public boolean saveLog(LogEmp logEmp) {
    return logEmpMapper.saveLog(logEmp) == 1;
  }
}