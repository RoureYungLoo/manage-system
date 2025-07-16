package com.luruoyang.mapper;

import com.luruoyang.model.pojo.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogMapper {
  List<Log> getLogs();

  void logEntryToDB(Log logEntry);
}
