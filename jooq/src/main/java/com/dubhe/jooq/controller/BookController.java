package com.dubhe.jooq.controller;

import com.dubhe.jooq.model.tables.pojos.Book;
import com.dubhe.jooq.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> getAll() {
        return bookService.getAuthors();
    }

    @PostMapping("/books")
    public void add(@RequestBody Book book) {
        bookService.add(book);
    }


}
