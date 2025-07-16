package com.luruoyang.service.impl;

import com.luruoyang.model.dto.ExprDto;
import com.luruoyang.mapper.EmpExprMapper;
import com.luruoyang.model.pojo.EmpExpr;
import com.luruoyang.service.EmpExprService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmpExprServiceImpl implements EmpExprService {

  @Autowired
  private EmpExprMapper empEXprMapper;

  @Override
  public boolean saveBatch(List<EmpExpr> exprList) {


    empEXprMapper.saveBatch(exprList);
    return true;
  }

  @Override
  public boolean deleteBatch(List<Long> empIds) {
    log.info("批量删除工作经历: {}", empIds);

    if (null == empIds || empIds.isEmpty()) return false;

    empEXprMapper.deleteBatch(empIds);

    return true;
  }

  @Override
  public Integer deleteById(Long id) {
    return empEXprMapper.deleteById(id);
  }

  @Override
  public List<EmpExpr> findByEmpId(Long id) {

    return empEXprMapper.findExprsByEmpId(id);

  }

  @Override
  public List<EmpExpr> findAll() {
    return empEXprMapper.findAll();
  }

  @Override
  public List<ExprDto> findByEmpIds(List<Long> empIds) {
   return empEXprMapper.findExprsByEmpIds(empIds);
//    return null;
  }
}
