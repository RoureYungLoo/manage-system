package com.luruoyang.service.impl;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luruoyang.enums.LoginEnum;
import com.luruoyang.enums.OptRes;
import com.luruoyang.enums.OptType;
import com.luruoyang.exception.*;
import com.luruoyang.mapper.EmpMapper;
import com.luruoyang.model.dto.*;
import com.luruoyang.model.pojo.Emp;
import com.luruoyang.model.pojo.EmpExpr;
import com.luruoyang.model.pojo.LogEmp;
import com.luruoyang.model.vo.LoginVo;
import com.luruoyang.service.EmpExprService;
import com.luruoyang.service.EmpService;
import com.luruoyang.service.LogEmpService;
import com.luruoyang.utils.JjwtUtils;
import com.luruoyang.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class EmpServiceImpl implements EmpService {

  @Autowired
  private EmpMapper empMapper;

  // @Autowired
  // private EmpExprMapper exprMapper;

  @Autowired
  private EmpExprService exprService;

  @Autowired
  private LogEmpService logEmpService;

  @Autowired
  private JjwtUtils jjwtUtils;

  @Override
  public List<Emp> findAll() throws Exception {
    return empMapper.findAll();
  }


  /**
   * 根据ID删除员工
   *
   * @param id
   * @return
   * @throws Exception
   */

  @Override
  public boolean deleteById(Long id) throws Exception {

    // 删除员工记录
    empMapper.deleteById(id);

    // 删除员工工作经历
    exprService.deleteById(id);
    return true;
  }

  /**
   * 根据ID批量删除员工
   *
   * @param empIds
   * @return
   * @throws Exception
   */
  @Override
  public boolean deleteByIds(List<Long> empIds) throws Exception {
    log.info("批量删除员工ID: {}", empIds);
    LogEmp.LogEmpBuilder logBuilder = LogEmp.builder()
        .optTime(LocalDateTime.now())
        .optType(OptType.delete.getCode())
        .optRes(OptRes.success.getCode())
        .preData(JSON.toJSONString(findByIds(empIds)));

    /* 判空校验 */
    if (empIds == null || empIds.isEmpty()) return false;

    try {
      empMapper.deleteBatch(empIds);
      exprService.deleteBatch(empIds);
    } catch (Exception e) {

      throw e;
    } finally {
      logBuilder.postData("");
      LogEmp logEmp = logBuilder.build();
      logEmpService.saveLog(logEmp);
    }

    return true;
  }

  @Override
  public List<Emp> findByIds(List<Long> empIds) {
    return empMapper.findByIds(empIds);
  }

  /**
   * 添加员工
   *
   * @param emp
   * @return
   * @throws MyException
   */

  @Override
  public boolean save(Emp emp) {
    /* 准备记录日志 */
    LogEmp logEmpEntry = LogEmp.builder()
        .preData("")
        .postData("")
        .optTime(LocalDateTime.now())
        .optType(OptType.insert.getCode())
        .optRes(OptRes.success.getCode()) // 假定操作成功
        .build();

    try {
      String password = emp.getPassword();
      if (Objects.isNull(password) || password.isEmpty()) {
        emp.setPassword("123456");
      }
      emp.setCreateTime(LocalDateTime.now());
      emp.setUpdateTime(LocalDateTime.now());

      empMapper.insert(emp);

      /* set expr empId */
      List<EmpExpr> exprList = emp.getExprList();
      if (!exprList.isEmpty()) {
        for (EmpExpr empExpr : exprList) {
          empExpr.setTbEmpId(emp.getId());
        }
        /* insert employ experience */
        exprService.saveBatch(exprList);
      }

      if (emp.getSalary() <= 3000) {
        // throw new Exception(""); // 编译期异常
        throw new ClientSideException("工资太低了, 狗都不干");
      }
    } catch (Exception e) {
      /* 操作失败 */
      logEmpEntry.setOptRes(OptRes.failed.getCode());
      throw e;
    } finally {
      /* save the log */
      logEmpEntry.setPostData(JSON.toJSONString(emp));
      logEmpService.saveLog(logEmpEntry);
    }

    return true;
  }

  /**
   * 更新员工信息
   *
   * @param emp
   * @return
   */
  @Override
  public boolean updateEmpById(Emp emp) {

    Emp empDB = empMapper.getById(emp.getId());

    // 记录日志
    LogEmp.LogEmpBuilder builder = LogEmp.builder()
        .optType(OptType.update.getCode())
        .optTime(LocalDateTime.now())
        .preData(JSON.toJSONString(empDB))
        .optRes(OptRes.success.getCode());

    try {
      /* 更新员工基本信息 */
      emp.setUpdateTime(LocalDateTime.now());
      empMapper.updateEmpById(emp);

      Long empId = emp.getId();

      /* 删除旧的工作经历 */
      exprService.deleteById(empId);

      /* 添加新的工作经历 */
      List<EmpExpr> exprList = emp.getExprList();
      if (!exprList.isEmpty()) {
        exprList.forEach(empExpr -> empExpr.setTbEmpId(empId));
        exprService.saveBatch(exprList);
      }
    } catch (Exception e) {
      builder.optRes(OptRes.failed.getCode());
      throw e;
    } finally {
      LogEmp logEmp = builder.postData(JSON.toJSONString(emp)).build();
      logEmpService.saveLog(logEmp);
    }

    return true;
  }

  /**
   * 有两种方法 [记得练习]
   *
   * @param id
   * @return
   */
  @Override
  public Emp findById(Long id) {
    // 查询员工表左连接工作经历表
    return empMapper.getById(id);
  }

  public Emp findByIdVersion2(Long id) {
    // 查询员工表
    Emp emp = empMapper.getById(id);
    // 查询工作经历表
    List<EmpExpr> exprList = exprService.findByEmpId(id);
    emp.setExprList(exprList);

    return emp;
  }


  @Override
  public PageResult<Emp> getEmpPage(EmpPageParam empPageParam) {

    Integer offset = (empPageParam.getPage() - 1) * empPageParam.getPageSize();
    Integer pageSize = empPageParam.getPageSize();
    List<Emp> emps = empMapper.getEmpPage(empPageParam, offset, pageSize);
    PageResult<Emp> pageResult = PageResult.getResult(emps, empMapper.count());

    log.info("EmpService pageResult : {}", pageResult);
    return pageResult;
  }

  /**
   * PageHelper
   *
   * @param param
   * @return
   */
  @Override
  public PageResult<Emp> getEmpUsePageHelper(EmpPageParam param) throws Exception {
    PageHelper.startPage(param.getPage(), param.getPageSize());

    // 先查询主表分页数据
    List<Emp> emps = empMapper.getEmpPage2(param); // [bug]
//    // 获取主表ID集合
//    List<Long> empIds = emps.stream().map(Emp::getId).collect(Collectors.toList());
//    // 批量查询员工工作经历
//    List<ExprDto> exprsByEmpIds = exprService.findByEmpIds(empIds);
//    HashMap<Long, List<EmpExpr>> map = new HashMap<>();
//    /* [empId: [EmpExpr]] */
//    exprsByEmpIds.forEach(exprDto -> map.put(exprDto.getEmpId(), exprDto.getExprs()));
//    // 组合数据
//    emps.forEach(emp -> emp.setExprList(map.get(emp.getId())));
    PageInfo<Emp> pageInfo = new PageInfo<>(emps);
    PageResult<Emp> pageResult = PageResult.getResult(pageInfo.getList(), pageInfo.getTotal());

    return pageResult;
  }

  @Override
  public List<EmpGenderDto> genderStatistics() {
    List<EmpGenderDto> empGenderDtos = empMapper.genderStatistics();
    return empGenderDtos;
  }

  @Override
  public Map<String, List> jobStatistics() {
    List<EmpJobDto> empJobDtos = empMapper.jobStatistics();

    Map<String, List> map = new HashMap<>();
    map.put("jobList", new ArrayList<String>());
    map.put("dataList", new ArrayList<Integer>());
    for (EmpJobDto empJobDto : empJobDtos) {
      map.get("jobList").add(empJobDto.getJobName());
      map.get("dataList").add(empJobDto.getJobCount());
    }

    log.info("员工职位统计: {}", map);
    return map;
  }

  @Override
  public Long findEmpCountByDeptId(Long deptId) {
    return empMapper.getCountByDeptId(deptId);
  }

  @Override
  public EmpJobDtoForTest jobStatisticsForTest() {
    List<EmpJobDtoForTest> empJobDtoForTest = empMapper.jobStatisticsForTest();
    log.info("jobStatisticsForTest {}", empJobDtoForTest);
    return null;
  }


  @Override
  public LoginVo login(LoginInfoDto loginInfo) {
    Emp emp = empMapper.login(loginInfo);
    Objects.requireNonNull(emp, "用户不存在");

    String password = emp.getPassword();
    String password1 = loginInfo.getPassword();

    if (Objects.isNull(password1)
        || Objects.isNull(password)
        || !password1.equals(password)) {
      throw new LoginException(LoginEnum.ERR_NOT_MATCH);
    }

    Map<String, Object> claims = new HashMap<>();
    claims.put("id", emp.getId());
    claims.put("username", emp.getUsername());
    claims.put("name", emp.getName());

    String token = jjwtUtils.generateToken(claims);

    LoginVo loginVo = LoginVo.builder()
        .id(emp.getId())
        .name(emp.getName())
        .username(emp.getUsername())
        .token(token)
        .build();

    return loginVo;
  }


  public PageResult<Emp> getEmpUsePageHelper2(EmpPageParam param) {

    PageHelper.startPage(param.getPage(), param.getPageSize());
    List<Emp> emps = empMapper.getEmpPage2(param);
    Page<Emp> empPage = (Page<Emp>) emps;

    log.info("getEmpUsePageHelper: {}", empPage);

    PageResult<Emp> pageResult = PageResult.getResult(empPage.getResult(), empPage.getTotal());

    return pageResult;
  }

}
