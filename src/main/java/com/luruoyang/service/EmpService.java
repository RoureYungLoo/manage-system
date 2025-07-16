package com.luruoyang.service;

import com.luruoyang.model.dto.*;
import com.luruoyang.model.pojo.Emp;
import com.luruoyang.model.vo.LoginVo;
import com.luruoyang.utils.PageResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface EmpService {
  public List<Emp> findAll() throws Exception;

  @Transactional(rollbackFor = Exception.class)
  boolean deleteByIds(List<Long> ids) throws Exception;

  boolean deleteById(Long id) throws Exception;

  List<Emp> findByIds(List<Long> empIds);

  //  @Transactional
  @Transactional(rollbackFor = {Exception.class})
  boolean save(Emp emp) throws Exception;

  @Transactional
  boolean updateEmpById(Emp emp);

  Emp findById(Long id);

  PageResult<Emp> getEmpPage(EmpPageParam empPageParam);

  PageResult<Emp> getEmpUsePageHelper(EmpPageParam empPageParam) throws Exception;

  List<EmpGenderDto> genderStatistics();

  Map<String, List> jobStatistics();

  Long findEmpCountByDeptId(Long deptId);

  EmpJobDtoForTest jobStatisticsForTest();

  LoginVo login(LoginInfoDto loginInfo);

}
