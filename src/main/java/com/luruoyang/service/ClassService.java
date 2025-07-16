package com.luruoyang.service;

import com.luruoyang.model.dto.ClassQueryParam;
import com.luruoyang.model.pojo.Clazz;
import com.luruoyang.utils.PageResult;

import java.util.List;


public interface ClassService {
  PageResult<Clazz> findPage(ClassQueryParam classParam);
  PageResult<Clazz> findPage2(ClassQueryParam classParam);

  boolean deleteById(Integer id);

  boolean save(Clazz clazz);

  Clazz findById(Long id);

  boolean updateById(Clazz clazz);

  List<Clazz> findList();

}
