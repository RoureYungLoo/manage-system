package com.luruoyang.model.dto;

import com.luruoyang.model.pojo.EmpExpr;
import lombok.Data;

import java.util.List;

@Data
public class ExprDto {
  private Long empId;
  private List<EmpExpr> exprs;
}
