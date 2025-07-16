package com.luruoyang.mapper;

import com.luruoyang.model.dto.ExprDto;
import com.luruoyang.model.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpExprMapper {

  int saveBatch(List<EmpExpr> exprList);

  int update(EmpExpr empExpr);

  int deleteBatch(List<Long> ids);

  Integer deleteById(Long id);

  List<EmpExpr> findExprsByEmpId(Long id);

  List<EmpExpr> findAll();


  List<ExprDto> findExprsByEmpIds(List<Long> empIds);

  List<Map<Map<String, Long>, List<Map<String, EmpExpr>>>> findExprsMapByEmpIds(List<Long> empIds);
}
