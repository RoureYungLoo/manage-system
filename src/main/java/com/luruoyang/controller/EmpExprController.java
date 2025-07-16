package com.luruoyang.controller;

import com.luruoyang.model.dto.ExprDto;
import com.luruoyang.model.pojo.EmpExpr;
import com.luruoyang.service.EmpExprService;
import com.luruoyang.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expr")
public class EmpExprController {

  @Autowired
  private EmpExprService exprService;

  /**
   * 批量添加
   *
   * @param exprList
   * @return
   */
  @PostMapping
  public Result saveBatch(List<EmpExpr> exprList) {
    exprService.saveBatch(exprList);
    return Result.success();
  }


  @GetMapping
  public Result findAll() {
    return Result.success(exprService.findAll());
  }

  @GetMapping("/{empId}")
  public Result getExprsByEmpId(@PathVariable Long empId) {
    List<EmpExpr> exprs = exprService.findByEmpId(empId);
    return exprs.isEmpty() ? Result.fail() : Result.success(exprs);
  }

  @DeleteMapping("/{empId}")
  public Result deleteExprsByEmpId(@PathVariable Long empId) {
    exprService.deleteById(empId);
    return Result.success();
  }

  @GetMapping("/ids")
  public Result findExprsByEmpIds(@RequestParam("ids") List<Long> empIds) {
    List<ExprDto> exprsByEmpIds = exprService.findByEmpIds(empIds);
    return Result.success(exprsByEmpIds);
  }
}
