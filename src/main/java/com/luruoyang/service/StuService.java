package com.luruoyang.service;

import com.luruoyang.model.dto.StuDegreeDto;
import com.luruoyang.model.dto.StuPageParam;
import com.luruoyang.model.pojo.Stu;
import com.luruoyang.utils.PageResult;

import java.util.List;
import java.util.Map;

public interface StuService {
  PageResult<Stu> findPage(StuPageParam stuParam);

  boolean deleteBatch(List<Long> ids);

  boolean save(Stu stu);

  boolean deleteById(Long id);

  Stu findStuById(Long id);

  boolean updateStuById(Stu stu);

  boolean violation(Long id, Integer score);

  List<StuDegreeDto> degereeStatistic();

  Map<String, List> groupByClazz();
}
