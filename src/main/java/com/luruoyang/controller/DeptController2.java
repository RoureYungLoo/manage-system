package com.luruoyang.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/depts2")
public class DeptController2 {

  /* 查询所有 */
  @RequestMapping(method = RequestMethod.GET)
  public String findAll() {
    return "findAll";
  }

  /* 查询单个 */
  @RequestMapping(method = RequestMethod.GET, value = "/{id}")
  public String findById(@PathVariable("id") Long id) {
    return "findById";
  }

  /* 删除单个 */
  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
  public String deleteById(@PathVariable("id") Long id) {
    return "deleteById";
  }

  /* 部分更新 */
  @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
  public String updateById(@PathVariable("id") Long id, String username) {
    return "updateById";
  }

  /* 更新全部 */
  @RequestMapping(method = RequestMethod.PUT)
  public String update(Object o) {
    return "update";
  }

  /* 保存一个 */
  @RequestMapping(method = RequestMethod.POST)
  public String save(Object o) {
    return "save";
  }
}
