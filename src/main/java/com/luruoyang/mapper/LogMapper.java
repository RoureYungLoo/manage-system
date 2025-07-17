package com.luruoyang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luruoyang.model.pojo.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogMapper extends BaseMapper<Log> {
  List<Log> getLogs();

  void logEntryToDB(Log logEntry);
}
