package com.luruoyang.model.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class DeptDto {
  private Integer id;
  private String name;
  private Integer pageNo;
  private Integer pageSize;
  private LocalDate startTime;
  private LocalDate endTime;
}
