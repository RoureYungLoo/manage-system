package com.luruoyang.controller;

import com.luruoyang.model.dto.EmpGenderDto;
import com.luruoyang.model.dto.EmpJobDtoForTest;
import com.luruoyang.model.dto.StuDegreeDto;
import com.luruoyang.service.EmpService;
import com.luruoyang.service.StuService;
import com.luruoyang.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {

  @Autowired
  private EmpService empService;

  @Autowired
  private StuService stuService;

  @GetMapping("/empGenderData")
  public Result genderStatistics() {

    List<EmpGenderDto> empGenderDto =
        empService.genderStatistics();

    return empGenderDto != null ? Result.success(empGenderDto) : Result.fail();
  }

  @GetMapping("/empJobData")
  public Result jobStatistics() {

    Map<String, List> empJobDtos =
        empService.jobStatistics();

    return empJobDtos != null ? Result.success(empJobDtos) : Result.fail();
  }

  @GetMapping("/empJobDataForTest")
  public Result jobStatisticsForTest() {

    EmpJobDtoForTest empJobDtoForTest =
        empService.jobStatisticsForTest();

    return Result.success(empJobDtoForTest);
  }

  @GetMapping("/studentDegreeData")
  public Result studentDegreeData() {

    List<StuDegreeDto> degreeDtos = stuService.degereeStatistic();

    return degreeDtos != null ? Result.success(degreeDtos) : Result.fail();
  }

  @GetMapping("/studentCountData")
  public Result studentCountData() {
    Map<String, List> stuCountMap = stuService.groupByClazz();
    return stuCountMap != null ? Result.success(stuCountMap) : Result.fail();
  }
}
