package com.luruoyang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luruoyang.model.pojo.LogEmp;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogEmpMapper extends BaseMapper<LogEmp> {
  int saveLog(LogEmp logEmp);
}
