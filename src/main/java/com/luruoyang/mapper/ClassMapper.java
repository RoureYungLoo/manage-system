package com.luruoyang.mapper;

import com.luruoyang.model.dto.ClassQueryParam;
import com.luruoyang.model.pojo.Clazz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassMapper {
  List<Clazz> findPage(@Param("classParam") ClassQueryParam classParam, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
  Long findMatchCount(@Param("classParam") ClassQueryParam classParam);

  int deleteById(Integer id);

  int save(Clazz clazz);

  Clazz findById(Long id);

  int updateById(Clazz clazz);

  List<Clazz> findList();

  Long count();
}
