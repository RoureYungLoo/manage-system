package com.luruoyang.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

//@Aspect
//@Component
//@Order(2)
public class AddAspect {

  @Around("@annotation(com.luruoyang.annotation.Add)")
  public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {

    Object[] args = pjp.getArgs();
    Object arg = args[0];

    Class<?> clazz = arg.getClass();

    /* 拿到类的class对象后, 有2中方法调用setter方法 */
    // 方法一  通过反射
    // Method setCreateTime = clazz.getDeclaredMethod("setCreateTime", LocalDateTime.class);
    // Method setUpdateTime = clazz.getDeclaredMethod("setUpdateTime", LocalDateTime.class);

    // setCreateTime.invoke(arg, LocalDateTime.now());
    // setUpdateTime.invoke(arg, LocalDateTime.now());

    // 方法二 通过 PropertyDescriptor
    PropertyDescriptor updateTime = new PropertyDescriptor("updateTime", clazz);
    PropertyDescriptor createTime = new PropertyDescriptor("createTime", clazz);

    Method setUpdateTime = updateTime.getWriteMethod();
    Method setCreateTime = createTime.getWriteMethod();

    setCreateTime.invoke(arg, LocalDateTime.now());
    setUpdateTime.invoke(arg, LocalDateTime.now());


//    Object returnValue = pjp.proceed(args);
    Object returnValue = pjp.proceed();

    return returnValue;
  }
}
