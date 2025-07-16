package com.luruoyang.config;

import com.luruoyang.interceptor.AInterceptor;
import com.luruoyang.interceptor.BInterceptor;
import com.luruoyang.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 标识这个类是配置类
public class WebMvcConfig implements WebMvcConfigurer {

  @Autowired
  private LoginInterceptor loginInterceptor;

//  @Autowired
//  private BInterceptor bInterceptor;

//  @Autowired
//  private AInterceptor aInterceptor;

  /* 按照注册顺序进行拦截 */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginInterceptor)
        .addPathPatterns("/**") // 拦截所有
        .excludePathPatterns("/login", "/sessions/**", "/cookie/**"); // 放行 login
//    registry.addInterceptor(bInterceptor)
//        .addPathPatterns("/**") // 拦截所有
//        .excludePathPatterns("/login"); // 放行 login
//    registry.addInterceptor(aInterceptor)
//        .addPathPatterns("/**") // 拦截所有
//        .excludePathPatterns("/login"); // 放行 login
  }
}
