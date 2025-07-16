package com.luruoyang.mapper;

import com.luruoyang.model.pojo.LogEmp;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogEmpMapper {
  int saveLog(LogEmp logEmp);
}
