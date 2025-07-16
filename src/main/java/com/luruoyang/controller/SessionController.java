package com.luruoyang.controller;

import com.alibaba.fastjson.JSON;
import com.luruoyang.model.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/sessions")
public class SessionController {

  @GetMapping("/set")
  public ResponseEntity<Object> setSession(String username,
                                           String password,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {

    HttpSession session = request.getSession();

    String sessionId = session.getId();
    log.info("------> /sessions/set sessionId: {}", sessionId);

    session.setAttribute("loginInfo", new User(username, password));

    return ResponseEntity.ok(JSON.toJSONString(session));
  }

  @GetMapping("/get")
  public ResponseEntity<Object> getSession(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession();

    String sessionId = session.getId();
    log.info("------> /sessions/get sessionId: {}", sessionId);

    Object loginInfo = session.getAttribute("loginInfo");

    return ResponseEntity.ok(loginInfo);
  }
}

