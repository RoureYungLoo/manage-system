package com.luruoyang.controller;

import com.luruoyang.annotation.LogOperation;
import com.luruoyang.model.dto.StuPageParam;
import com.luruoyang.model.pojo.Stu;
import com.luruoyang.service.StuService;
import com.luruoyang.utils.PageResult;
import com.luruoyang.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@Slf4j
public class StuController {

  @Autowired
  private StuService stuService;


  @GetMapping
  public Result page(StuPageParam stuParam) {
    PageResult<Stu> pr = stuService.findPage(stuParam);
    return pr != null ? Result.success(pr) : Result.fail();
  }

/*  @DeleteMapping("/{idstr}")
  public Result deleteBatch(@PathVariable("idstr") String idsStr) {
    String[] split = idsStr.split(",");
    List<Long> ids = Arrays.stream(split).map(Long::parseLong).collect(Collectors.toList());
    if (stuService.deleteBatch(ids)) {
      return Result.success(true);
    } else {
      return Result.fail();
    }
  }*/

  @DeleteMapping("/{idstr}")
  @LogOperation
  public Result deleteBatch(@PathVariable("idstr") List<Long> ids) {
    if (stuService.deleteBatch(ids)) {
      return Result.success(true);
    } else {
      return Result.fail();
    }
  }

  @PostMapping
  @LogOperation
  public Result save(@RequestBody Stu stu) {
    if (stuService.save(stu)) {
      return Result.success(true);
    } else {
      return Result.fail();
    }
  }

  @GetMapping("/{id}")
  public Result findStuById(@PathVariable Long id) {
    Stu stu = stuService.findStuById(id);

    return stu != null ? Result.success(stu) : Result.fail();

  }

  @PutMapping
  @LogOperation
  public Result updateStuById(@RequestBody Stu stu) {
    if (stuService.updateStuById(stu)) {
      return Result.success(true);
    } else {
      return Result.fail();
    }
  }

  /* /students/violation/{id}/{score} */
  @PutMapping("/violation/{id}/{score}")
  @LogOperation
  public Result violation(@PathVariable("id") Long id, @PathVariable("score") Integer score) {
    return stuService.violation(id, score) ? Result.success() : Result.fail();
  }
}
