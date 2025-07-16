package com.luruoyang.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Slf4j
//@WebFilter("/*")
public class AFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("----> call AFilter#init()");

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    log.info("----> call AFilter#doFilter() start");
    chain.doFilter(request, response);
    log.info("----> call AFilter#doFilter() end");
  }

  @Override
  public void destroy() {
    log.info("----> call AFilter#destroy()");

  }
}
