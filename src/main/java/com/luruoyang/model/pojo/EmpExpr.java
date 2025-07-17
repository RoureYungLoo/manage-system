package com.luruoyang.model.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName(value = "tb_empexpr")
public class EmpExpr {
  private Long id;
  private String company;
  private String job;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate begin;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate end;
  private Long tbEmpId;
}
