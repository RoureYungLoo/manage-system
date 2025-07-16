package com.luruoyang;

import com.luruoyang.model.pojo.User;
import com.luruoyang.utils.JjwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
class ManageSystemApplicationTests {
//  @Autowired
//  SqlSession sqlSession;

  @Autowired
  private JjwtUtils jjwtUtils;

  @Test
  void testPageHelperRowBounds() {
    /* rowbounds 方式调用 */
//    List<Emp> list = sqlSession.selectList("select * from tb_emp", null, new RowBounds(0, 10));

//    System.out.println(list);
  }

  @Test
  public void test() {
    String getenv = System.getenv("OSS_ACCESS_KEY_ID ");
    System.out.println(getenv);
    String accessKeyId = System.getProperty("OSS_ACCESS_KEY_ID");
    System.out.println(accessKeyId);
  }

  /* 读取环境变量 */
  @Test
  public void test2() {
    ProcessBuilder pb = new ProcessBuilder();
    Map<String, String> env = pb.environment();
    String OSS_ACCESS_KEY_ID = env.get("OSS_ACCESS_KEY_ID");
    String OSS_ACCESS_KEY_SECRET = env.get("OSS_ACCESS_KEY_SECRET");
    System.out.println("OSS_ACCESS_KEY_ID: " + OSS_ACCESS_KEY_ID);
    System.out.println("OSS_ACCESS_KEY_SECRET: " + OSS_ACCESS_KEY_SECRET);
  }


/*
  @Test
  public void testJwt() {
    String s = JwtUtils.genToken("lisi");
    System.out.println(s);
  }
*/

/*  @Test
  public void testJwtParse(){
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Imxpc2kiLCJqdGkiOiI3NzdhMDg3My04ZDQ5LTRkNTQtYTI5Yi1hM2Q1ZTY5NDY1ZGEiLCJleHAiOjE3NTEyODU1NjB9.tBMb76Vmbf4iL1z0K6Em6FNnfdqVeJXxfJk40LfVvAo";
    Jws<Claims> claimsJws = JwtUtils.parseToken(token);
    System.out.println(claimsJws);
  }*/

  @Test
  public void testGenToken() {
    HashMap<String, Object> claims = new HashMap<>();
    claims.put("username", "lisi");
    claims.put("age", 213);
    claims.put("address", "2wefjfowe");
    claims.put("loginUser", new User("lisi", "123456"));
    String token = jjwtUtils.generateToken(claims);
    log.info(token);
  }

  @Test
  public void testParseToken() {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJsb2dpblVzZXIiOnsidXNlcm5hbWUiOiJsaXNpIiwicGFzc3dvcmQiOiIxMjM0NTYifSwiYWRkcmVzcyI6IjJ3ZWZqZm93ZSIsImV4cCI6MTc1MTM3NjIwMCwiaWF0IjoxNzUxMzQwMjAwLCJhZ2UiOjIxMywidXNlcm5hbWUiOiJsaXNpIn0.Rd77n5DSWy7jnWZ-rOS9DSMxEg8RBEpnwJo8kJnicG4";
    Claims claims = jjwtUtils.parseToken(token);
    log.info("---> claims: {}", claims);
  }
}
