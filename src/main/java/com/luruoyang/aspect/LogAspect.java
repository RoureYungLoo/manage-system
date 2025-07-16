package com.luruoyang.aspect;

import com.alibaba.fastjson2.JSONObject;
import com.luruoyang.context.Context;
import com.luruoyang.model.pojo.Log;
import com.luruoyang.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

//@Component
@Aspect
public class LogAspect {

  @Autowired
  private LogService logService;

//  @Pointcut("@annotation(log)")
//  public void pointcut(LogOperation log) {
//  }

  /* 匹配 save 开头的所有业务层方法 */
  @Pointcut("execution(* com.luruoyang.service.*.save*(..))")
  public void save() {
  }

  /* 匹配 delete 开头的所有业务层方法 */
  @Pointcut("execution(* com.luruoyang.service.*.delete*(..))")
  public void delete() {
  }

  /* 匹配 update 开头的所有业务层方法 */
  @Pointcut("execution(* com.luruoyang.service.*.update*(..))")
  public void update() {
  }

  //  @Around(value = "pointcut(logg)", argNames = "pjp,logg")
//  @Around("execution(* com.luruoyang.service.*.save*(..))")
  @Around("save()||delete()||update()")
  public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {

    Object result = null;
    long start = System.currentTimeMillis();

    try {
      /* 前置通知, 可以修改参数 */
      result = pjp.proceed();
      /* 后置通知, 可以修改返回值 */
      return result;
    } catch (Throwable e) {
      /* 异常通知 */
      throw e;
    } finally {
      long end = System.currentTimeMillis();
      long cost = end - start;

      Log logEntry = Log.builder()
          .operateEmpId(Long.valueOf(Context.getEmpId().toString()))
          .operateTime(LocalDateTime.now())
          .className(pjp.getTarget().getClass().getName())
          .methodName(pjp.getSignature().getName())
          .methodParams(Arrays.toString(pjp.getArgs()))
          .returnValue(JSONObject.toJSONString(result))
          .costTime(cost)
          .build();

      /* 注意不能以save命名, 否则会导致递归aop */
      logService.logEntryToDB(logEntry);
    }
  }
}
