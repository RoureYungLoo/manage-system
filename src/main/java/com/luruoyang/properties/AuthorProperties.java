package com.luruoyang.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "author")
public class AuthorProperties {
//  @Value("${author.name}")
  private String name;

//  @Value("${author.nickname}")
  private String nickname;

//  @Value("${author.birth}")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birth;
//
//  @Value("${author.career}")
  private String career;

//  @Value("${author.college}")
  private String college;

//  @Value("${author.hometown}")
  private String hometown;

//  @Value("${author.uid}")
  private Long uid;

//  @Value("${author.type}")
  private Short type;
}
