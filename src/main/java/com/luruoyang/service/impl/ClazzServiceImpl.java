package com.luruoyang.service.impl;

//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.model.dto.ClazzQueryParam;
import com.luruoyang.enums.ClazzStatus;
import com.luruoyang.enums.ClientError;
import com.luruoyang.exception.ClientSideException;
import com.luruoyang.mapper.ClazzMapper;
import com.luruoyang.model.pojo.Clazz;
import com.luruoyang.model.pojo.Emp;
import com.luruoyang.service.ClazzService;
import com.luruoyang.service.EmpService;
import com.luruoyang.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {

  @Autowired
  private ClazzMapper clazzMapper;

  @Autowired
  private EmpService empService;

  /**
   * 手动进行条件分页
   *
   * @param clazzParam
   * @return
   */
  @Override
  public PageResult<Clazz> findPage(ClazzQueryParam clazzParam) {

    Integer pageNo = clazzParam.getPage();
    Integer pageSize = clazzParam.getPageSize();
    Integer offset = null;

    if (Objects.nonNull(pageNo) && Objects.nonNull(pageSize)) {
      offset = (pageNo - 1) * pageSize;
    }

    /* 条件分页结果集 */
    List<Clazz> clazzes = clazzMapper.findPage(clazzParam, offset, pageSize);
    /* 条件分页, 符合条件的总记录数 */
    Long total = clazzMapper.findMatchCount(clazzParam);

    return PageResult.getResult(clazzes, total);
  }

  /**
   * PageHelper 分页
   *
   * @param clazzParam
   * @return
   */
  @Override
  public PageResult<Clazz> findPage2(ClazzQueryParam clazzParam) {
    String name = clazzParam.getName();

    IPage<Clazz> iPage = new Page<>(clazzParam.getPage(), clazzParam.getPageSize());
    LambdaQueryWrapper<Clazz> lqw = Wrappers.lambdaQuery();
    lqw.like(StringUtils.hasText(name), Clazz::getName, name);

    IPage<Clazz> page = this.page(iPage, lqw);
    List<Clazz> clazzList = page.getRecords();
    clazzList.forEach(clazz -> {
      Integer masterId = clazz.getMasterId();
      Emp emp = empService.getById(masterId);
      clazz.setMasterName(emp.getName());
    });

    return PageResult.getResult(clazzList, page.getTotal());
  }

  @Override
  public boolean deleteById(Integer id) {
    return clazzMapper.deleteById(id) == 1;
  }

  /**
   * add 班级
   *
   * @param clazz
   * @return
   */
  @Override
  public boolean save(Clazz clazz) {
    LocalDate beginDate = clazz.getBeginDate();
    LocalDate endDate = clazz.getEndDate();

    if (!beginDate.isBefore(endDate)) {
      throw new ClientSideException(ClientError.INVALID_DATE);
    }

    clazz.setCreateTime(LocalDateTime.now());
    clazz.setUpdateTime(LocalDateTime.now());

    if (endDate.isBefore(LocalDate.now())) {
      clazz.setStatus(ClazzStatus.Ended);
    } else if (beginDate.isAfter(LocalDate.now())) {
      clazz.setStatus(ClazzStatus.NotStarted);
    } else {
      clazz.setStatus(ClazzStatus.Running);
    }

    return clazzMapper.save(clazz) == 1;
  }


  @Override
  public Clazz findById(Long id) {
    return clazzMapper.findById(id);
  }

  @Override
  public boolean updateById(Clazz clazz) {
    return clazzMapper.updateById(clazz) == 1;
  }

  @Override
  public List<Clazz> findList() {
    return clazzMapper.findList();
  }


}
