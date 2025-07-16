package com.luruoyang.controller;

import com.luruoyang.properties.AuthorProperties;
import com.luruoyang.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/author")
public class AuthorController {

  @Autowired
//  private Author author;
  private AuthorProperties author;

  @GetMapping
  public ResponseEntity<Result> getAuthor() {
    return ResponseEntity.ok(Result.success(author));
  }
}
