package com.luruoyang.filter;

import com.luruoyang.utils.JjwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
//@Component // 启动类上添加 @ServletComponentScan // 二选一
//@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

  //  @Autowired
  private JjwtUtils jjwtUtils;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("----> call LoginFilter#init()");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    log.info("----> call LoginFilter#doFilter() start");
    log.info("---> req.getHeader(\"Token\"): {}", req.getHeader("Token"));

    String token = req.getHeader("Token");

// 放行 login

    String uri = req.getRequestURI();
    if (uri.equalsIgnoreCase("/login") || uri.equalsIgnoreCase("/login/")) {
      chain.doFilter(request, response);
      return;
    }

// 其他路径都要拦截

    if (!jjwtUtils.check(token)) {
      resp.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    chain.doFilter(request, response);
    log.info("----> call LoginFilter#doFilter() end");

  }

  @Override
  public void destroy() {
    log.info("----> call LoginFilter#destroy()");

  }
}
