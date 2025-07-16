package com.luruoyang.utils;

import com.luruoyang.context.Context;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class JjwtUtils {
  @Value("${jjwt.secret}")
  private String secret;
  @Value("${jjwt.expiration}")
  private long expiration;

  /**
   * 生成JWT Token
   *
   * @param claims 载荷内容
   * @return token字符串
   */
  public String generateToken(Map<String, Object> claims) {
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
        .compact();
  }

  /**
   * 解析JWT Token
   *
   * @param token token字符串
   * @return 载荷内容
   * @throws JwtException token无效或过期时抛出
   */
  public Claims parseToken(String token) throws JwtException {
    return Jwts.parser()
        .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
        .parseClaimsJws(token)
        .getBody();
  }

  public boolean check(String token) {
    boolean flag = false;
    if (!Objects.isNull(token) && !token.isEmpty()) {
      Claims claims = null;
      try {
        claims = parseToken(token);
      } catch (Exception e) {
        e.printStackTrace();
        log.error("req token parse failed: {}", e.getMessage());
        throw e;
      } finally {
        //System.out.println("****======>   finally 块");
      }

      //System.out.println("****======>   finally 块 后面的");

      /* 不出异常即为Token解析成功 */
      flag = true;
      /* token 解析成功 */
      /* 设置当前用户的empId, 这里会出现问题, 由于是静态的, 会导致每个具有合法Token的用户, 将Context中的map更新为自己的empId,  */
      Object id = claims.get("id");
      Context.setEmpId(id);
    }
    return flag;
  }
}