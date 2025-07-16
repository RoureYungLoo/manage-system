package com.luruoyang.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luruoyang.annotation.Add;
import com.luruoyang.annotation.Update;
import com.luruoyang.model.dto.DeptDto;
import com.luruoyang.enums.BusinessErrorEnum;
import com.luruoyang.enums.ClientError;
import com.luruoyang.exception.BusinessException;
import com.luruoyang.exception.ClientSideException;
import com.luruoyang.mapper.DeptMapper;
import com.luruoyang.model.pojo.Dept;
import com.luruoyang.service.DeptService;
import com.luruoyang.service.EmpService;
import com.luruoyang.utils.PageDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

  @Autowired
  private DeptMapper deptMapper;

  @Autowired
  private EmpService empService;

  @Override
  public List<Dept> findAll() {
    return deptMapper.selectList(null);
  }

  /**
   * delete a department by its id
   *
   * @param id department id
   * @return true if success, false if fail
   */
  @Override
  public boolean deleteById(Long id) {
    Long empCountsUnderDept = empService.findEmpCountByDeptId(id);
    if (empCountsUnderDept > 0) throw new BusinessException(BusinessErrorEnum.DEPT_NOT_EMPTY);

    return deptMapper.deleteById(id) == 1;
  }

  @Override
  @Add
  public boolean save(Dept dept) {
    dept.setCreateTime(LocalDateTime.now());
    dept.setUpdateTime(LocalDateTime.now());
    return deptMapper.insert(dept) == 1;
  }

  @Override
  public Dept findById(Long id) {
    return deptMapper.selectById(id);
  }

  @Override
  @Update
  public boolean updateById(Long id, Dept dept) {
    Dept dbDept = deptMapper.selectById(id);
    if (dbDept == null) return false;

    BeanUtils.copyProperties(dept, dbDept);
    dbDept.setUpdateTime(LocalDateTime.now());

//    return deptMapper.updateById(id, dept) == 1;
    return deptMapper.updateById(dbDept) == 1;
  }

  /**
   * 更新部门
   *
   * @param dept
   * @return
   */
  @Override
  @Update
  public boolean update(Dept dept) {
    Dept dbDept = deptMapper.selectById(dept.getId());
    if (dbDept == null) throw new ClientSideException(ClientError.NO_SUCH_DEPT);

    BeanUtils.copyProperties(dept, dbDept);
    dbDept.setUpdateTime(LocalDateTime.now());

    int affected = 0;

    try {
      affected = deptMapper.updateById(dbDept);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ClientSideException(ClientError.DEPT_NAME_EXISTS);
    }

    return affected == 1;
  }


  @Override
  public List<Dept> findPage2(Integer pageNo, Integer pageSize) {
    PageHelper.startPage(pageNo, pageSize);
    List<Dept> all = deptMapper.selectList(null);
    PageInfo<Dept> pageInfo = new PageInfo<>(all);
    List<Dept> deptList = pageInfo.getList();
    return deptList;
  }

  @Override
  public List<Dept> findQuery(DeptDto deptDto) {
    System.out.println(deptDto);
    return deptMapper.findQuery(deptDto);
  }

  @Override
  public List<Dept> findPage(PageDto pageDto) {
    return deptMapper.findPage((pageDto.getPageNo() - 1) * pageDto.getPageSize(), pageDto.getPageSize());
  }


}
