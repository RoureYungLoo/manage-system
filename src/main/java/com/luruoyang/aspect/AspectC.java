package com.luruoyang.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
//@Aspect
public class AspectC {

  @Pointcut("execution(* com.luruoyang.service.EmpService.*(..))")
  public void pointcut() {

  }


  @Around("pointcut()")
  public Object aroundAdvice(ProceedingJoinPoint pjp) {
    Object returnValue = null;
    try {
      log.warn("AspectC 前置通知");
      returnValue = pjp.proceed();
      log.warn("AspectC 后置通知");
    } catch (Throwable e) {
      log.warn("AspectC 异常通知: {}", e.getMessage());
      throw new RuntimeException(e);
    } finally {
      log.warn("AspectC 最终通知");
    }
    return returnValue;
  }

}
