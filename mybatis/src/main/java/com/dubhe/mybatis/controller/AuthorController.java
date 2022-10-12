package com.dubhe.mybatis.controller;

import com.dubhe.mybatis.entity.Author;
import com.dubhe.mybatis.mapper.AuthorMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

  @Autowired
  private AuthorMapper authorMapper;

  @PostMapping("/authors")
  public String createAuthor() {
    Author author = new Author();
    author.setName(RandomStringUtils.randomAlphabetic(6));
    author.setAge(RandomUtils.nextInt(10, 100));

    authorMapper.insert(author);

    return author.getName();
  }
}
