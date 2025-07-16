package com.luruoyang.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {
  private Long id;
  private Long operateEmpId;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime operateTime;
  private String className;
  private String methodName;
  private String methodParams;
  private String returnValue;
  private Long costTime;
}
