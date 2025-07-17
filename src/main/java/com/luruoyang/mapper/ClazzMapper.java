package com.luruoyang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luruoyang.model.dto.ClazzQueryParam;
import com.luruoyang.model.pojo.Clazz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClazzMapper extends BaseMapper<Clazz> {
  List<Clazz> findPage(@Param("classParam") ClazzQueryParam classParam, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
  Long findMatchCount(@Param("classParam") ClazzQueryParam classParam);

  int deleteById(Integer id);

  int save(Clazz clazz);

  Clazz findById(Long id);

  int updateById(Clazz clazz);

  List<Clazz> findList();

  Long count();
}
