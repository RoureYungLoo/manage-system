package com.luruoyang.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
//@Aspect
//@Component
public class EmpLogAspect {


//  @After("execution(* com.luruoyang.mapper.EmpMapper.*(..))")
  public void afterAdvice(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;

    log.info("----------> After Advice, {},{}", methodSignature.getName(), args);
  }

  @Around("execution(*  com.luruoyang.service..*(..))")
  public Object aroundAdvice(ProceedingJoinPoint pjp) {

    Object[] args = pjp.getArgs();
    Object returnValue = null;


    try {
      log.info("============> before advice 可以修改参数:{}", args);
      returnValue = pjp.proceed(args);
      log.info("============> afterReturning advice, 可以修改返回值:{}", returnValue);
      return returnValue;
    } catch (Throwable e) {
      log.info("============> afterThrowing advice, 发生异常了: {}", e.getMessage());
      throw new RuntimeException(e);
    } finally {
      log.info("============> after advice, 方法执行完了");
    }
  }
}
