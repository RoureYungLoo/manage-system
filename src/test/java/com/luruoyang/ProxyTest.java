package com.luruoyang;

import com.luruoyang.service.EmpService;
import org.aspectj.lang.annotation.Around;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


@SpringBootTest
public class ProxyTest {
  @Autowired
  private EmpService empService;

  @Test
  public void testCGLIBProxy() {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(A.class);
    enhancer.setCallback(new MyCallback());
    A a = (A) enhancer.create();
    a.method1();

  }


}

class MyCallback implements MethodInterceptor {

  @Override
  public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

    System.out.println("方法调用前");
    Object returnValue;
    try {
      returnValue = methodProxy.invokeSuper(o, args);
      System.out.println("方法调用后");
    } catch (Exception e) {
      System.out.println("异常发生后: " + e.getMessage());
      throw new RuntimeException(e);
    } finally {
      System.out.println("最终finally");
    }

    return returnValue;
  }
}

class A {
  public void method1() {
    System.out.println("method1");
    int i = 1 / 0;
  }

  public void method2() {
    System.out.println("method2");

  }

  public void func1() {
    System.out.println("func1");

  }

  public void func2() {
    System.out.println("func2");

  }
}