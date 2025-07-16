package com.luruoyang.controller;

import com.luruoyang.annotation.LogOperation;
import com.luruoyang.model.dto.EmpDto;
import com.luruoyang.model.dto.EmpPageParam;
import com.luruoyang.model.pojo.Emp;
import com.luruoyang.service.EmpService;
import com.luruoyang.utils.PageResult;
import com.luruoyang.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin

@RestController
@RequestMapping("/emps")
public class EmpController {

  public static final Logger logger = LoggerFactory.getLogger(EmpController.class);

  @Autowired
  private EmpService empService;

  /* 增 */
  @PostMapping
  @LogOperation
  public Result save(@RequestBody Emp emp) throws Exception {
    if (empService.save(emp)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  /* 删 */
  @LogOperation
  @DeleteMapping("/{id}")
  public Result deleteById(@PathVariable("id") Long id) throws Exception {
    if (empService.deleteById(id)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  /**
   * 批量删除
   *
   * @param empIds
   * @return
   * @throws Exception
   */
  @DeleteMapping()
  @LogOperation
  public Result deleteByIds(@RequestParam("ids") List<Long> empIds) throws Exception {
    return Result.success(empService.deleteByIds(empIds));
  }

  // public Result deleteByIds(@RequestParam("ids") String empIds) throws
  // Exception {
  // public Result deleteByIds(@RequestParam("ids") Long[] empIds) throws
  // Exception {
  // public Result deleteByIds(@RequestParam("ids") List<Long> empIds) throws
  // Exception {
  // String[] idsStrArr = empIds.split(",");

  // List<Long> _empIds =
  // Arrays.stream(idsStrArr).map(Long::parseLong).collect(Collectors.toList());

  // return Result.success(empService.deleteByIds(_empIds));
  // return Result.success(empService.deleteByIds(List.of(empIds)));
  // return Result.success(empService.deleteByIds(empIds));
  // }

  /* 改 */
  @LogOperation
  @PutMapping
  public Result updateEmpById(@RequestBody Emp emp) {
    boolean b = empService.updateEmpById(emp);
    return b ? Result.success() : Result.fail();
  }

  /* 查询所有 */
  @GetMapping("/list")
  public Result findAll(EmpDto empDto) throws Exception {
    logger.info("find all {}", empDto);
    List<Emp> emps = empService.findAll();
    return emps != null ? Result.success(emps) : Result.fail();
  }

  /* 查询所有 分页 */

  @GetMapping
  public Result getEmpPage(EmpPageParam empPageParam) throws Exception {
    logger.info("员工分页查询参数 {}", empPageParam);
    // PageResult<Emp> pageResult = empService.getEmpPage(empPageParam);
    PageResult<Emp> pageResult = empService.getEmpUsePageHelper(empPageParam);
    return Result.success(pageResult);
  }

  /* 查询单个 */
  @GetMapping("/{id}")
  public Result findById(@PathVariable("id") Long id) throws Exception {
    Emp emp = empService.findById(id);
    return emp != null ? Result.success(emp) : Result.fail();
  }

  /* 批量查询 */
  @GetMapping("/ids")
  public Result findById(@RequestParam("ids") List<Long> ids) throws Exception {
    List<Emp> emps = empService.findByIds(ids);
    return emps != null ? Result.success(emps) : Result.fail();
  }

}
