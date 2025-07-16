package com.luruoyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//@ServletComponentScan // 扫描自定义过滤器
public class ManageSystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(ManageSystemApplication.class, args);
  }

}
