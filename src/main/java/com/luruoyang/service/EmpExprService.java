package com.luruoyang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luruoyang.model.dto.ExprDto;
import com.luruoyang.model.pojo.EmpExpr;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmpExprService extends IService<EmpExpr> {

  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  boolean saveExprList(List<EmpExpr> exprList);

  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  boolean deleteExprList(List<Long> empIds);

  boolean deleteByExprListById(Long id);

  List<EmpExpr> findByEmpId(Long id);

  List<EmpExpr> findAll();

  List<ExprDto> findByEmpIds(List<Long> empIds);

}
