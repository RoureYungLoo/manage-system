package com.luruoyang.controller;

import com.alibaba.fastjson.JSON;
import com.luruoyang.model.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/cookies")
public class CookieController {

  @GetMapping("/add")
  public ResponseEntity<Cookie> setCookie(
      String username, String password,
      HttpServletRequest request,
      HttpServletResponse response) {

    String jsonString = JSON.toJSONString(new User(username, password));
    /* UTF编码, 否则报错 */
    String value = null;
    try {
      value = URLEncoder.encode(jsonString, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    Cookie cookie = new Cookie("userInfo", value);
    log.info("------> setCookie: {}", cookie);
    cookie.setMaxAge(60 * 10);
    response.addCookie(cookie);


    return ResponseEntity.ok(cookie);
  }

  @GetMapping("/get")
  public ResponseEntity<Cookie[]> getCookie(HttpServletRequest request, HttpServletResponse response) {
    Cookie[] cookies = request.getCookies();
    return ResponseEntity.ok(cookies);
  }
}
