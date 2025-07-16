package com.luruoyang.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
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
