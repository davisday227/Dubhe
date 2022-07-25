package com.dubhe.jooq.controller;

import com.dubhe.jooq.model.tables.pojos.Author;
import com.dubhe.jooq.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public List<Author> getAll() {
        return authorService.getAuthors();
    }

    @PostMapping("/authors")
    public void add(@RequestBody Author author) {
        authorService.add(author);
    }


}
