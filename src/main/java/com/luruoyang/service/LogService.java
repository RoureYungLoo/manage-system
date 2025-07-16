package com.luruoyang.service;

import com.luruoyang.model.pojo.Log;
import com.luruoyang.utils.PageResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface LogService {
  PageResult<Log> getLogs(Integer page, Integer pageSize);

  /* 添加事务, 保证日志记录 */
  @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
  void logEntryToDB(Log logEntry);
}
