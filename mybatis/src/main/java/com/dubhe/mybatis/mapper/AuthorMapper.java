package com.dubhe.mybatis.mapper;

import com.dubhe.mybatis.entity.Author;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorMapper {

  void insert(Author author);
}
