//package com.luruoyang.aspect;
//
//import com.alibaba.fastjson.JSON;
//import com.luruoyang.annotation.LogOperation;
//import com.luruoyang.pojo.Log;
//import com.luruoyang.service.LogService;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//
////@Component
////@Aspect
//public class LogAspect2 {
//
//  @Autowired
//  private LogService logService;
//
///*  @Pointcut("@annotation(log)")
//  public void pointcut(LogOperation log) {
//  }
//
//  @Pointcut("@annotation(com.luruoyang.annotation.LogOperation)")
//  public void logOperation() {
//  }
//  */
//
//  @Around("execution(* com.luruoyang.service.*.*(..))")
//  public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
//    Object result = null;
//    long start = System.currentTimeMillis();
//
//    try {
//      result = pjp.proceed();
//      return result;
//    } finally {
//      long end = System.currentTimeMillis();
//      long cost = end - start;
//
//      Log logEntry = Log.builder()
//          .operateEmpId(1L)
//          .operateTime(LocalDateTime.now())
//          .className(pjp.getTarget().getClass().getName())
//          .methodName(pjp.getSignature().getName())
//          .methodParams(Arrays.toString(pjp.getArgs()))
//          .returnValue(JSON.toJSONString(result))
//          .costTime(cost).build();
//      logService.save(logEntry);
//    }
//  }
//}
