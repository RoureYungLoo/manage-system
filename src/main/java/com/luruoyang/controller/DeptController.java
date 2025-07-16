package com.luruoyang.controller;

import com.luruoyang.annotation.LogOperation;
import com.luruoyang.model.dto.DeptDto;
import com.luruoyang.model.pojo.Dept;
import com.luruoyang.service.DeptService;
import com.luruoyang.utils.PageDto;
import com.luruoyang.utils.PageResult;
import com.luruoyang.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/depts")
@Slf4j
public class DeptController {

  @Autowired
  private DeptService deptService;

  /* 查询所有 */
  @GetMapping
  public Result findAll() {
    Result result = null;
    List<Dept> depts = deptService.findAll();
    if (Objects.isNull(depts) || depts.isEmpty()) {
      result = Result.fail();
    } else {
      result = Result.success(depts);

    }
    return result;
  }

  /* 条件查询 */
  @PostMapping("/query")
  public Result findQuery(@RequestBody DeptDto deptDto) {
    Result result = null;
    List<Dept> depts = deptService.findQuery(deptDto);
    if (Objects.isNull(depts) || depts.isEmpty()) {
      result = Result.fail();
    } else {
      result = Result.success(depts);
    }
    return result;
  }


  /* 分页查询, 基于 LIMIT offset count */
  @GetMapping("/page")
  public Result findPage(PageDto pageDto) {
    Result result = null;
    List<Dept> depts = deptService.findPage(pageDto);
    if (Objects.isNull(depts) || depts.isEmpty()) {
      result = Result.fail();
    } else {
      result = Result.success(depts);
    }
    return result;
  }

  /* 使用 PageHelper 进行分页查询 */
  @GetMapping("/page2")
  public Result findPage2(Integer pageNo, Integer pageSize) {
    Result result = null;
    List<Dept> depts = deptService.findPage2(pageNo, pageSize);
    if (Objects.isNull(depts) || depts.isEmpty()) {
      result = Result.fail();
    } else {
      result = Result.success(depts);
    }
    return result;
  }

  @GetMapping("/page3")
  public Result findPage3(DeptDto deptDto) {
    PageResult<Dept> r = deptService.findPage3(deptDto);
    return Result.success(r);
  }

  /* 查询单个 */
  @GetMapping("/{id}")
  public Result findById(@PathVariable("id") Long id) {
    Result result = null;
    Dept dept = deptService.findById(id);
    if (Objects.isNull(dept)) {
      result = Result.fail();
    }

    result = Result.success(dept);

    return result;
  }

  /* 删除单个 */
  @DeleteMapping("/{id}")
  @LogOperation
  public Result deleteById(@PathVariable("id") Long id) {
    if (deptService.deleteById(id)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

  @DeleteMapping
  @LogOperation
  public Result deleteById2(@RequestParam Long id) {
    if (deptService.deleteById(id)) {
      return Result.success();
    } else {
      return Result.fail();
    }
  }

//  @DeleteMapping
//  public Result deleteById3(HttpServletRequest request) {
//
//    String id = request.getParameter("id");
//
//    System.out.println(id);
//    return null;
//  }

  /* 部分更新 */
  @PatchMapping("/{id}")
  public Result updateById(@PathVariable("id") Long id, @RequestBody Dept dept) {
    Result result = null;
    if (deptService.updateById(id, dept)) {
      result = Result.success();
    } else {
      result = Result.fail();
    }

    return result;
  }

  /* 更新全部 */
  @PutMapping
  @LogOperation
  public Result update(@RequestBody Dept dept) {
    Result result = null;
    if (deptService.update(dept)) {
      result = Result.success();
    } else {
      result = Result.fail();
    }

    return result;
  }

  /**
   * 添加部门
   *
   * @param dept
   * @return
   */
  @PostMapping
  @LogOperation
  public Result save(@RequestBody Dept dept) {
    Result result = null;

    if (deptService.save(dept)) {
      result = Result.success();
    } else {
      result = Result.fail();
    }

    return result;
  }
}
