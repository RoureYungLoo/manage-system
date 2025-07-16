package com.luruoyang;

import com.luruoyang.model.dto.LoginInfoDto;
import com.luruoyang.mapper.EmpMapper;
import com.luruoyang.model.pojo.Emp;
import com.luruoyang.model.vo.LoginVo;
import com.luruoyang.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
@SpringBootTest
public class AOPTest {

  @Autowired
  private EmpService empService;

  /* 动态代理修改返回值 */
  @Test
  public void testProxyEmpService() {

    EmpService proxy = (EmpService) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{EmpService.class}, new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        /* 修改参数 */


        if (args[0] instanceof LoginInfoDto) {
          LoginInfoDto loginDto = (LoginInfoDto) args[0];
          loginDto.setUsername(loginDto.getUsername().trim());
          loginDto.setPassword(loginDto.getPassword().trim());
          args[0] = loginDto;
        }


        long start = System.currentTimeMillis();
        log.info("{}: {}", method.getName(), args);
        Object returnValue = method.invoke(empService, args);
        long end = System.currentTimeMillis();

        /* 修改返回值 */
        if (returnValue instanceof Emp) {
          Emp emp = ((Emp) returnValue);
          emp.setPassword("******");
          return emp;
        }

        log.info("{}执行耗时:{}ms", method.getName(), end - start);

        return returnValue;
      }
    });

    Emp emp = proxy.findById(10L);
    log.info("查询结果:{}", emp);

    LoginInfoDto loginInfoDto = new LoginInfoDto();
    loginInfoDto.setUsername("     songjiang    ");
    loginInfoDto.setPassword("123456");
    LoginVo login = proxy.login(loginInfoDto);
    System.out.println(login);


  }

  @Autowired
  private EmpMapper empMapper;

  @Test
  public void TestProxyEmpMapper() {
    EmpMapper proxy = (EmpMapper) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{EmpMapper.class}, new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object returnValue = null;
        if (method.getName().contains("insert") ||
            method.getName().contains("delete") ||
            method.getName().contains("update")) {
          try {
            returnValue = method.invoke(empMapper, args);
          } catch (Exception e) {
            log.info("{}执行失败了, 参数:{}, 返回值:{}", method.getName(), args, returnValue);
            throw e;
          } finally {
            log.info("{}执行成功了, 参数:{}, 返回值:{}", method.getName(), args, returnValue);
          }
        }

        return method.invoke(empMapper, args);
      }
    });

    Integer integer = proxy.deleteById(145L);
    System.out.println("===================>" + integer);
  }

}
