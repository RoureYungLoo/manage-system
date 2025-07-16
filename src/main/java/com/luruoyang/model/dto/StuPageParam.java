package com.luruoyang.model.dto;

import lombok.Data;

@Data
public class StuPageParam {
  private String name;
  private Integer degree;
  private Integer clazzId;
  private Integer page;
  private Integer pageSize;
}
