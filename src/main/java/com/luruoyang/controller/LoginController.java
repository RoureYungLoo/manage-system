package com.luruoyang.controller;

import com.luruoyang.annotation.LogOperation;
import com.luruoyang.enums.LoginEnum;
import com.luruoyang.model.dto.LoginInfoDto;
import com.luruoyang.model.vo.LoginVo;
import com.luruoyang.service.EmpService;
import com.luruoyang.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping
public class LoginController {

  @Autowired
  private EmpService empService;

  @PostMapping("/login/cookie")
  @LogOperation
  public Result loginCookie(@RequestBody LoginInfoDto loginInfo, HttpServletResponse response) {
    LoginVo loginVo = empService.login(loginInfo);

    Cookie cookie = new Cookie("username", loginVo.getName());
    response.addCookie(cookie);
    return loginVo != null ? Result.success(loginVo) : Result.fail();
  }

  @PostMapping("/login/session")
  @LogOperation
  public Result login(@RequestBody LoginInfoDto loginInfo, HttpServletRequest request,
                      HttpServletResponse response) {

    String cookie1 = request.getHeader("Cookie");
    Cookie[] cookies = request.getCookies();
    log.info("------> cookie1: {}", cookie1);
    log.info("------> cookies: {}", cookies);

    LoginVo loginVo = empService.login(loginInfo);
    Cookie cookie = new Cookie("username", loginVo.getName());
    cookie.setMaxAge(60 * 10);
    response.addCookie(cookie);

    return loginVo != null ? Result.success(loginVo) : Result.fail();
  }

  @PostMapping("/login2")
  @LogOperation
  public Result loginSession(@RequestBody LoginInfoDto loginInfo, HttpServletRequest request,
                             HttpServletResponse response) {

    log.info("------> cookies: {}");

    HttpSession session = request.getSession();
    String sessionId = session.getId();
    log.info("------> sessionId: {}", sessionId);

    LoginVo loginVo = empService.login(loginInfo);
    session.setAttribute("loginVo", loginVo);

    return loginVo != null ? Result.success(loginVo) : Result.fail();
  }

  @PostMapping("/login3")
  @LogOperation
  public Result loginVo(@RequestBody LoginInfoDto loginInfo) {

    LoginVo loginVo = empService.login(loginInfo);
    return loginVo != null ? Result.success(loginVo) : Result.fail();
  }

  @PostMapping("/login")
  @LogOperation
  public Result loginMap(@RequestBody LoginInfoDto loginInfo) {

    LoginVo LoginVo = empService.login(loginInfo);
    return LoginVo != null ? Result.success(LoginEnum.OK, LoginVo) : Result.fail();
  }
}
