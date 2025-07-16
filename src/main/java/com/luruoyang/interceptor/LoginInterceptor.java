package com.luruoyang.interceptor;

import com.luruoyang.utils.JjwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

  @Autowired
  private JjwtUtils jjwtUtils;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    log.info("******* LoginInterceptor#preHandle *******");
    String token = request.getHeader("Token");
    boolean flag = jjwtUtils.check(token);
    //token 检查未通过
    if (!flag) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      log.warn("---> [Interceptor] loginInterceptor preHandle jjwtUtils.check(token) failed");
    }
    return flag;
//    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    log.info("******* LoginInterceptor#postHandle *******");
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    log.info("******* LoginInterceptor#afterCompletion *******");
  }
}
