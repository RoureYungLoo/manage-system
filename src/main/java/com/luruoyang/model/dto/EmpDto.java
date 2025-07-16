package com.luruoyang.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class EmpDto {
  private String name;
  private Integer gender;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate begin;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate end;
  private Integer page;
  private Integer pageSize;
}
