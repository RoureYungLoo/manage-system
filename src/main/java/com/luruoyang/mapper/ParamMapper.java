package com.luruoyang.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ParamMapper {

  @Select("select #{age} from tb_emp;")
  int insert(Integer age);
}
