package com.luruoyang.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emp {
  private Long id;
  private String username;
  private String password;
  private String name;
  private Integer gender;
  private String phone;
  private Integer job;
  private Long salary;
  private String image;
  private Long deptId;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date entryDate;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;

  @TableField(exist = false)
  private String deptName;

  @TableField(exist = false)
  private List<EmpExpr> exprList;
}
