package com.luruoyang.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luruoyang.enums.ClazzStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Clazz {
  private Long id;
  private String name;
  private String room;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate beginDate;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;
  private Integer masterId;
  private Integer subject;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:dd")
  private LocalDateTime createTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:dd")
  private LocalDateTime updateTime;
  private String status;

  private String masterName;

  /* 记录总数 */
  private transient Long total;

  public void setStatus(ClazzStatus status) {
    this.status = status.Status();
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
