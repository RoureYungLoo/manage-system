package com.luruoyang.service.impl;

//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
import com.luruoyang.model.dto.ClassQueryParam;
import com.luruoyang.enums.ClazzStatus;
import com.luruoyang.enums.ClientError;
import com.luruoyang.exception.ClientSideException;
import com.luruoyang.mapper.ClassMapper;
import com.luruoyang.model.pojo.Clazz;
import com.luruoyang.service.ClassService;
import com.luruoyang.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ClassServiceImpl implements ClassService {

  @Autowired
  private ClassMapper classMapper;

  /**
   * 手动进行条件分页
   *
   * @param classParam
   * @return
   */
  @Override
  public PageResult<Clazz> findPage(ClassQueryParam classParam) {

    Integer pageNo = classParam.getPage();
    Integer pageSize = classParam.getPageSize();
    Integer offset = null;

    if (Objects.nonNull(pageNo) && Objects.nonNull(pageSize)) {
      offset = (pageNo - 1) * pageSize;
    }

    /* 条件分页结果集 */
    List<Clazz> clazzes = classMapper.findPage(classParam, offset, pageSize);
    /* 条件分页, 符合条件的总记录数 */
    Long total = classMapper.findMatchCount(classParam);

    return PageResult.getResult(clazzes, total);
  }

  /**
   * PageHelper 分页
   *
   * @param classParam
   * @return
   */
  @Override
  public PageResult<Clazz> findPage2(ClassQueryParam classParam) {
    // PageHelper.startPage(classParam.getPage(), classParam.getPageSize());
    // List<Clazz> clazzList = classMapper.findPage(classParam, null, null);
    // Page<Clazz> clazzPage = (Page<Clazz>) clazzList;
    // return PageResult.getResult(clazzPage.getResult(), clazzPage.getTotal());
    return null;
  }

  @Override
  public boolean deleteById(Integer id) {
    return classMapper.deleteById(id) == 1;
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

    return classMapper.save(clazz) == 1;
  }


  @Override
  public Clazz findById(Long id) {
    return classMapper.findById(id);
  }

  @Override
  public boolean updateById(Clazz clazz) {
    return classMapper.updateById(clazz) == 1;
  }

  @Override
  public List<Clazz> findList() {
    return classMapper.findList();
  }


}
