package com.luruoyang.controller;

import com.luruoyang.model.pojo.Log;
import com.luruoyang.service.LogService;
import com.luruoyang.utils.PageResult;
import com.luruoyang.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
@Slf4j
public class LogController {

  @Autowired
  private LogService logService;

  @GetMapping("/page")
  public Result getLogs(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer pageSize) {

    PageResult<Log> logs = logService.getLogs(page, pageSize);
    return Result.success(logs);
  }
}
