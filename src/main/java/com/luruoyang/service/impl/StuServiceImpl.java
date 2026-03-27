package com.luruoyang.service.impl;

//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.model.dto.StuClazzCountDto;
import com.luruoyang.model.dto.StuDegreeDto;
import com.luruoyang.model.dto.StuPageParam;
import com.luruoyang.mapper.StuMapper;
import com.luruoyang.model.pojo.Clazz;
import com.luruoyang.model.pojo.Stu;
import com.luruoyang.service.ClazzService;
import com.luruoyang.service.StuService;
import com.luruoyang.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class StuServiceImpl extends ServiceImpl<StuMapper, Stu> implements StuService {

  @Autowired
  private StuMapper stuMapper;

  @Autowired
  private ClazzService clazzService;

  @Override
  public PageResult<Stu> findPage(StuPageParam stuParam) {
    String name = stuParam.getName();
    Integer degree = stuParam.getDegree();
    Integer clazzId = stuParam.getClazzId();

    IPage<Stu> page = new Page<>(stuParam.getPage(), stuParam.getPageSize());
    LambdaQueryWrapper<Stu> wrapper = Wrappers.lambdaQuery();
    wrapper.like(StringUtils.hasText(name), Stu::getName, name)
        .eq(Objects.nonNull(degree), Stu::getDegree, degree)
        .eq(Objects.nonNull(clazzId), Stu::getClazzId, clazzId);

    IPage<Stu> stuPage = stuMapper.selectPage(page, wrapper);
    List<Stu> stuList = stuPage.getRecords();
    stuList.forEach(stu -> {
      Long tmp = stu.getClazzId();
      Clazz clazz = clazzService.getById(tmp);
      stu.setClazzName(clazz.getName());
    });

    return PageResult.getResult(stuList, stuPage.getTotal());
  }

  @Override
  public boolean deleteBatch(List<Long> ids) {
    // Integer deleteCount = stuMapper.deleteBatch(ids);
    Integer deleteCount = stuMapper.deleteByIds(ids);
    return deleteCount == ids.size();
  }

  @Override
  public boolean save(Stu stu) {
    stu.setCreateTime(LocalDateTime.now());
    stu.setUpdateTime(LocalDateTime.now());
    stu.setViolationCount(0);
    stu.setViolationScore(0);

    // return stuMapper.save(stu) == 1;
    return stuMapper.insert(stu) == 1;
  }

  @Override
  public boolean deleteById(Long id) {
    return false;
  }

  @Override
  public Stu findStuById(Long id) {
    return this.getById(id);
    //return stuMapper.findStuById(id);
  }

  @Override
  public boolean updateStuById(Stu stu) {
    // if (stuMapper.findStuById(stu.getId()) == null) {
    if (stuMapper.selectById(stu.getId()) == null) {
      return false;
    }

    stu.setUpdateTime(LocalDateTime.now());
    return this.updateById(stu);
    // return stuMapper.updateById(stu) == 1;
  }

  @Override
  public boolean violation(Long id, Integer score) {
    /* 是否能加分? */
    // Stu stuDB = stuMapper.findStuById(id);
    // stuDB.setViolationCount(stuDB.getViolationCount() + 1);
    // stuDB.setViolationScore(stuDB.getViolationScore() + score);
    // stuDB.setUpdateTime(LocalDateTime.now());
    // return stuMapper.updateById(stuDB) == 1;
    Stu stuDb = stuMapper.selectById(id);
    stuDb.setViolationCount(stuDb.getViolationCount() + 1);
    stuDb.setViolationScore(stuDb.getViolationScore() + score);
    stuDb.setUpdateTime(LocalDateTime.now());
    return stuMapper.updateById(stuDb) > 0;
  }

  @Override
  public List<StuDegreeDto> degereeStatistic() {
    QueryWrapper<Stu> wrapper = Wrappers.query();
    wrapper.select("degree as name", "count(degree) as value")
        .groupBy("degree");
    List<StuDegreeDto> stuDegreeDtos = stuMapper.selectObjs(wrapper);
    return stuDegreeDtos;
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
