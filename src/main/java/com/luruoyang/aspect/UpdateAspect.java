package com.luruoyang.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.weaver.ast.Var;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

//@Aspect
//@Component
//@Order(2)
public class UpdateAspect {


  @Around("@annotation(com.luruoyang.annotation.Update)")
  public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {

    Object[] args = pjp.getArgs();
    Object arg = args[0];

    Class<?> clazz = arg.getClass();

    // 方法一 通过反射设置
    // Method setUpdateTime = clazz.getDeclaredMethod("setUpdateTime", LocalDateTime.class);
    // setUpdateTime.invoke(arg, LocalDateTime.now());

    // 方法二 通过PropertyDescriptor
    PropertyDescriptor updateTime = new PropertyDescriptor("updateTime", clazz);
    Method setUpdateTime = updateTime.getWriteMethod();
    setUpdateTime.invoke(arg, LocalDateTime.now());


//    Object returnValue = pjp.proceed(args);
    Object returnValue = pjp.proceed();

    return returnValue;
  }
}
