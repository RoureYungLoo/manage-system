package com.luruoyang.model.dto;

import com.luruoyang.model.pojo.EmpExpr;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExprDto {
  private Long empId;
  private List<EmpExpr> exprs;
}
