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
public class AspectA {

  @Pointcut("execution(* com.luruoyang.service.EmpService.*(..))")
  public void pointcut() {

  }


  @Around("pointcut()")
//  @Around("execution(public * *..*.*(..))")
  public Object aroundAdvice(ProceedingJoinPoint pjp) {
    Object returnValue = null;
    try {
      log.warn("AspectA 前置通知");
      returnValue = pjp.proceed();
      log.warn("AspectA 后置通知");
    } catch (Throwable e) {
      log.warn("AspectA 异常通知: {}", e.getMessage());
      throw new RuntimeException(e);
    } finally {
      log.warn("AspectA 最终通知");
    }
    return returnValue;
  }

}
