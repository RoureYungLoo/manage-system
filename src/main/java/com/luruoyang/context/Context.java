package com.luruoyang.context;


import java.util.HashMap;
import java.util.Map;

public class Context {
  /* 将string改为线程, 保证key的唯一不重复 */
  public static Map<Thread, Object> map = new HashMap<>();

  public static Object getEmpId() {
    return map.get(Thread.currentThread());
  }

  public static void setEmpId(Object empId) {
    map.put(Thread.currentThread(), empId);
  }
}

/*public class Context {
 *//* 记录线程数据: empId, 并发不安全 并且 后来者覆盖之前用户的empId *//*
  public static Map<String, Object> map = new HashMap<>();

  public static Object getEmpId() {
    return map.get("empId");
  }

  public static void setEmpId(Object empId) {
    map.put("empId", empId);
  }
}*/
