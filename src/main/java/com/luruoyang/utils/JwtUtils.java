//package com.luruoyang.utils;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import io.jsonwebtoken.security.SecureDigestAlgorithm;
//
//import javax.crypto.SecretKey;
//import javax.security.auth.kerberos.EncryptionKey;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.Map;
//import java.util.UUID;
//
///* 最新版本的 */
//public class JwtUtils {
//  private static final int ACCESS_EXPIRE = 60 * 10;
//  private static final SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;
//  private static final String SECRET = "SecretKeySecretKeySecretKeySecretKey";
//
//  private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
//
//  public static String genToken(String username) {
//    String uuid = UUID.randomUUID().toString();
//    Date exprireDate = Date.from(Instant.now().plusSeconds(ACCESS_EXPIRE));
//
//    return Jwts.builder().header()
//        .add("typ", "JWT")
//        .add("alg", "HS256")
//        .and()
//        .claim("username", username)
//        .id(uuid)
//        .expiration(exprireDate)
//        .signWith(SECRET_KEY, ALGORITHM)
//        .compact();
//  }
//
//  public static Jws<Claims> parseToken(String token) {
//    return Jwts.parser()
//        .verifyWith(SECRET_KEY)
//        .build().parseSignedClaims(token);
//  }
//
//}
