package com.luruoyang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luruoyang.model.dto.ClazzQueryParam;
import com.luruoyang.model.pojo.Clazz;
import com.luruoyang.utils.PageResult;

import java.util.List;


public interface ClazzService extends IService<Clazz> {
  PageResult<Clazz> findPage(ClazzQueryParam classParam);
  PageResult<Clazz> findPage2(ClazzQueryParam classParam);

  boolean deleteById(Integer id);

  boolean save(Clazz clazz);

  Clazz findById(Long id);

  boolean updateById(Clazz clazz);

  List<Clazz> findList();

}
