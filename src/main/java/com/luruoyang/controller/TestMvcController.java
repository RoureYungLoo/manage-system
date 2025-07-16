package com.luruoyang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luruoyang.model.pojo.Dept;
import com.luruoyang.model.pojo.Emp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Enumeration;

@RestController
@RequestMapping("/mvc")
public class TestMvcController {

  /* 手动处理请求体 JSON , reqeust.getReader() */
  @GetMapping("/get/json")
  public String get(HttpServletRequest request) throws Exception {
    StringBuilder builder = new StringBuilder();
    BufferedReader reader = request.getReader();
    String string;
    /* 读取请求体 */
    while ((string = reader.readLine()) != null) {
      builder.append(string);
    }

    String requestBodyStr = builder.toString();

    /*JSON转换 */
    ObjectMapper objectMapper = new ObjectMapper();
    Dept requestBody = objectMapper.readValue(requestBodyStr, Dept.class);

    System.out.println(requestBody);

    return builder.toString();

  }

  /* 手动处理路径参数 request.getRequestURI()*/
  @GetMapping("/pathvar/{id}/{name}/{addr}")
  public String testPathVariable(HttpServletRequest request) {
    return request.getRequestURI();
  }

  /* 手动处理 GET Query String , request.getParameter()*/
  @GetMapping
  public String testGetQueryString(HttpServletRequest request) {
    Enumeration<String> parameterNames = request.getParameterNames();
    System.out.println(parameterNames);

    return "success";
  }

  @GetMapping("/responseentity")
  public ResponseEntity<Emp> testResponseEntity() {
    Emp emp = new Emp();
    emp.setId(555L);
    emp.setImage("cag.pjg");
    emp.setJob(1);
    emp.setName("lisi");
    emp.setUsername("lisiiiiii");

    return ResponseEntity.ok(emp);
  }
}
