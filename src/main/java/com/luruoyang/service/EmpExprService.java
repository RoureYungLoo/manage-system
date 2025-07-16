package com.luruoyang.service;

import com.luruoyang.model.dto.ExprDto;
import com.luruoyang.model.pojo.EmpExpr;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmpExprService {

  boolean saveBatch(List<EmpExpr> exprList);

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  boolean deleteBatch(List<Long> empIds);

  Integer deleteById(Long id);

  List<EmpExpr> findByEmpId(Long id);

  List<EmpExpr> findAll();

  List<ExprDto> findByEmpIds(List<Long> empIds);

}
