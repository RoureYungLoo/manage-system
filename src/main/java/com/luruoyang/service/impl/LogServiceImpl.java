package com.luruoyang.service.impl;

//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
import com.luruoyang.mapper.LogMapper;
import com.luruoyang.model.pojo.Log;
import com.luruoyang.service.LogService;
import com.luruoyang.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LogServiceImpl implements LogService {

  @Autowired
  private LogMapper logMapper;

  @Override
  public PageResult<Log> getLogs(Integer page, Integer pageSize) {
    // PageHelper.startPage(page, pageSize);
    // List<Log> logs = logMapper.getLogs();
    // Page<Log> logPage = (Page<Log>) logs;
    // PageResult<Log> logPageResult = PageResult.getResult(logPage.getResult(), logPage.getTotal());
    // return logPageResult;
    return null;
  }

  @Override
  public void logEntryToDB(Log logEntry) {
//    log.warn("log entry: {}", logEntry);
    logMapper.logEntryToDB(logEntry);
  }
}
