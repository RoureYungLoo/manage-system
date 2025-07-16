package com.luruoyang.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfiguration {

  @Bean
  public PaginationInnerInterceptor paginationInnerInterceptor() {
    PaginationInnerInterceptor interceptor = new PaginationInnerInterceptor();
    interceptor.setDbType(DbType.MYSQL);
    return interceptor;
  }
}
