package com.luruoyang.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.annotation.Add;
import com.luruoyang.annotation.Update;
import com.luruoyang.model.dto.DeptDto;
import com.luruoyang.enums.BusinessErrorEnum;
import com.luruoyang.enums.ClientError;
import com.luruoyang.exception.BusinessException;
import com.luruoyang.exception.ClientSideException;
import com.luruoyang.mapper.DeptMapper;
import com.luruoyang.model.pojo.Dept;
import com.luruoyang.model.pojo.Emp;
import com.luruoyang.service.DeptService;
import com.luruoyang.service.EmpService;
import com.luruoyang.utils.PageDto;
import com.luruoyang.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

  @Autowired
  private DeptMapper deptMapper;

  @Autowired
  private EmpService empService;

  @Override
  public List<Dept> findAll() {
    // return deptMapper.selectList(null);
    return this.list();
  }

  /**
   * delete a department by its id
   *
   * @param id department id
   * @return true if success, false if fail
   */
  @Override
  public boolean deleteById(Long id) {
    LambdaQueryWrapper<Emp> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Objects.nonNull(id), Emp::getDeptId, id);
    long empCountsUnderDept = empService.count(wrapper);
    if (empCountsUnderDept > 0) {
      throw new BusinessException(BusinessErrorEnum.DEPT_NOT_EMPTY);
    }
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
    if (dbDept == null) {
      log.warn("no such dept: id={}", id);
      return false;
    }

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
    if (dbDept == null) {
      throw new ClientSideException(ClientError.NO_SUCH_DEPT);
    }

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
    // PageHelper.startPage(pageNo, pageSize);
    // List<Dept> all = deptMapper.selectList(null);
    // PageInfo<Dept> pageInfo = new PageInfo<>(all);
    // List<Dept> deptList = pageInfo.getList();
    // return deptList;
    return null;
  }

  @Override
  public List<Dept> findQuery(DeptDto deptDto) {
    String name = deptDto.getName();
    Integer deptId = deptDto.getId();

    IPage<Dept> page = new Page<>(deptDto.getPageNo(), deptDto.getPageSize());
    LambdaQueryWrapper<Dept> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Objects.nonNull(deptId), Dept::getId, deptId)
        .like(StringUtils.hasText(name), Dept::getName, name);

    IPage<Dept> deptPage = this.page(page, wrapper);
    return deptPage.getRecords();
  }

  @Override
  public PageResult<Dept> findPage3(DeptDto deptDto) {
    /* 分页查询 */
    IPage<Dept> iPage = new Page<>(deptDto.getPageNo(), deptDto.getPageSize());

    /* 条件查询 */
    LambdaQueryWrapper<Dept> lqw = new LambdaQueryWrapper<>();
    Integer deptId = deptDto.getId();
    String deptDtoName = deptDto.getName();
    lqw.eq(Objects.nonNull(deptId), Dept::getId, deptId)
        .or(Objects.isNull(deptId))
        .like(StringUtils.hasText(deptDtoName), Dept::getName, deptDtoName)
        .orderByDesc(Dept::getUpdateTime);

    // 返回值 IPage<T>
    IPage<Dept> selectedPage = deptMapper.selectPage(iPage, lqw);

    // 返回值 List<T>
    // List<Dept> deptList = deptMapper.selectList(iPage, lqw);
    return PageResult.getResult(selectedPage.getRecords(), selectedPage.getTotal());
    // return PageResult.getResult(deptList, (long) deptList.size());
  }

  @Override
  public List<Dept> findPage(PageDto pageDto) {
    return deptMapper.findPage((pageDto.getPageNo() - 1) * pageDto.getPageSize(), pageDto.getPageSize());
  }


}
