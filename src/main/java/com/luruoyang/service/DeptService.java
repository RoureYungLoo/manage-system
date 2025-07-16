package com.luruoyang.service;

import com.luruoyang.model.dto.DeptDto;
import com.luruoyang.model.pojo.Dept;
import com.luruoyang.utils.PageDto;
import com.luruoyang.utils.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DeptService {
  List<Dept> findAll();

  boolean deleteById(Long id);

  boolean save(Dept dept);

  Dept findById(Long id);

  boolean updateById(Long id, Dept dept);

  boolean update(Dept dept);

  List<Dept> findPage(PageDto pageDto);

  List<Dept> findPage2(Integer pageNo, Integer pageSize);

  List<Dept> findQuery(DeptDto deptDto);

  PageResult<Dept> findPage3(DeptDto deptDto);
}
