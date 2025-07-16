package com.luruoyang.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class PageResult<T> {
  private Long total;
  private List<T> rows;

  private PageResult(List<T> rows, Long total) {
    this.rows = rows;
    this.total = total;
  }

  private PageResult() {
  }

  public static <T> PageResult<T> getResult(List<T> rows, Long total) {
    return new PageResult<>(rows, total);
  }
}
