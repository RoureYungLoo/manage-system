package com.luruoyang.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.luruoyang.model.dto.StuClazzCountDto;
import com.luruoyang.model.dto.StuDegreeDto;
import com.luruoyang.model.dto.StuPageParam;
import com.luruoyang.mapper.StuMapper;
import com.luruoyang.model.pojo.Stu;
import com.luruoyang.service.StuService;
import com.luruoyang.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StuServiceImpl implements StuService {

  @Autowired
  private StuMapper stuMapper;

  @Override
  public PageResult<Stu> findPage(StuPageParam stuParam) {
    PageHelper.startPage(stuParam.getPage(), stuParam.getPageSize());

    List<Stu> stuList = stuMapper.findPage(stuParam);

    Page<Stu> page = (Page<Stu>) stuList;

    PageResult<Stu> pr = PageResult.getResult(page.getResult(), page.getTotal());

    return pr;
  }

  @Override
  public boolean deleteBatch(List<Long> ids) {
    Integer deleteCount = stuMapper.deleteBatch(ids);
    return deleteCount == ids.size();
  }

  @Override
  public boolean save(Stu stu) {
    stu.setCreateTime(LocalDateTime.now());
    stu.setUpdateTime(LocalDateTime.now());
    stu.setViolationCount(0);
    stu.setViolationScore(0);

    return stuMapper.save(stu) == 1;
  }

  @Override
  public boolean deleteById(Long id) {
    return false;
  }

  @Override
  public Stu findStuById(Long id) {
    return stuMapper.findStuById(id);
  }

  @Override
  public boolean updateStuById(Stu stu) {
    if (stuMapper.findStuById(stu.getId()) == null) return false;

    stu.setUpdateTime(LocalDateTime.now());
    return stuMapper.updateById(stu) == 1;
  }

  @Override
  public boolean violation(Long id, Integer score) {

    /* 是否能加分? */
    Stu stuDB = stuMapper.findStuById(id);
    stuDB.setViolationCount(stuDB.getViolationCount() + 1);
    stuDB.setViolationScore(stuDB.getViolationScore() + score);
    stuDB.setUpdateTime(LocalDateTime.now());

    return stuMapper.updateById(stuDB) == 1;
  }

  @Override
  public List<StuDegreeDto> degereeStatistic() {
    return stuMapper.degreeStatistic();
  }

  @Override
  public Map<String, List> groupByClazz() {
    List<StuClazzCountDto> list = stuMapper.groupByClazz();
    Map<String, List> clazzMap = new HashMap<>();

    clazzMap.put("clazzList", new ArrayList<String>());
    clazzMap.put("dataList", new ArrayList<Integer>());

    for (StuClazzCountDto clazzCountDto : list) {
      clazzMap.get("clazzList").add(clazzCountDto.getClazzName());
      clazzMap.get("dataList").add(clazzCountDto.getClazzCount());
    }

    return clazzMap;
  }
}
