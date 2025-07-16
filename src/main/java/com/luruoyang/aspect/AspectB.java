package com.luruoyang.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
//@Aspect
//@Order(2) // 指定切面的顺序, 默认值21亿, 数组小的先执行
public class AspectB {

  @Pointcut("execution(* com.luruoyang.service.EmpService.*(..))")
  public void pointcut() {

  }


  @Around("pointcut()")
  public Object aroundAdvice(ProceedingJoinPoint pjp) {
    Object returnValue = null;
    try {
      log.warn("AspectB 前置通知");
      returnValue = pjp.proceed();
      log.warn("AspectB 后置通知");
    } catch (Throwable e) {
      log.warn("AspectB 异常通知: {}", e.getMessage());
      throw new RuntimeException(e);
    } finally {
      log.warn("AspectB 最终通知");
    }
    return returnValue;
  }

}
