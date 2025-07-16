package com.luruoyang.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
//@Aspect
//@Component
public class TimeCostAspect {

  /* 匹配所有Controller */
  @Pointcut("execution(* com.luruoyang.controller.*.*(..))")
  public void controllers() {
  }

  /* 匹配所有Service */
  @Pointcut("execution(* com.luruoyang.service.*.*(..))")
  public void services() {
  }

  /* 匹配所有Mapper */
  @Pointcut("execution(* com.luruoyang.mapper.*.*(..))")
  public void mappers() {
  }

//  @Around("execution( * com.luruoyang.service.*.*(..)))")
  @Around("controllers() || services() || mappers()")
  public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {

    long start = System.currentTimeMillis();
    try {
      return pjp.proceed();
    } finally {
      long end = System.currentTimeMillis();
      Signature signature = pjp.getSignature();
      String typeName = signature.getDeclaringTypeName();
      String name = signature.getName();
      log.warn("{}() 执行耗时: {}ms", typeName + "#" + name, end - start);
    }
  }
}
