package com.luruoyang.controller;

import com.luruoyang.annotation.LogOperation;
import com.luruoyang.model.dto.ClazzQueryParam;
import com.luruoyang.model.pojo.Clazz;
import com.luruoyang.service.ClazzService;
import com.luruoyang.utils.PageResult;
import com.luruoyang.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clazzs")

public class ClazzController {

  private static final Logger logger = LoggerFactory.getLogger(ClazzController.class);
  @Autowired
  private ClazzService clazzService;

  @GetMapping("/list")
  public Result findList() {
    logger.info("findList {}", "no param");
    List<Clazz> result = clazzService.findList();

    if (result == null) {
      return Result.fail();
    }

    return Result.success(result);
  }

  /**
   * 分页查询
   * @param classParam
   * @return
   */
  @GetMapping
  public Result findPage(ClazzQueryParam classParam) {
    logger.info("findPage {}", classParam);
//    PageResult<Clazz> result = clazzService.findPage(classParam);
    PageResult<Clazz> result = clazzService.findPage2(classParam);

    if (result == null) {
      return Result.fail();
    }

    return Result.success(result);
  }

  /**
   * 删除班级
   * @param id
   * @return
   */
  @DeleteMapping("/{id}")
  @LogOperation
  public Result deleteById(@PathVariable("id") Integer id) {
    logger.info("deleteById {}", id);
    if (clazzService.deleteById(id)) {
      return Result.success();
    }
    return Result.fail();
  }

  /**
   * 添加班级
   * @param clazz
   * @return
   */
  @PostMapping
  @LogOperation
  public Result save(@RequestBody Clazz clazz) {
    logger.info("save {}", clazz);
    if (clazzService.save(clazz)) {
      return Result.success();
    }
    return Result.fail();
  }

  /**
   * 根据ID查询
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  public Result findById(@PathVariable("id") Long id) {
    logger.info("findById {}", id);
    Clazz clazz = clazzService.findById(id);
    if (clazz == null) {
      return Result.fail();
    }
    return Result.success(clazz);
  }

  @PutMapping
  @LogOperation
  public Result updateById(@RequestBody Clazz clazz) {
    logger.info("updateById {}", clazz);
    if (clazzService.updateById(clazz)) {
      return Result.success(clazz);
    }
    return Result.fail();
  }
}
