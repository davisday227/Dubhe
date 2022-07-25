package com.dubhe.jooq.service;

import com.dubhe.jooq.model.Tables;
import com.dubhe.jooq.model.tables.pojos.Author;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    DSLContext dslContext;

    public List<Author> getAuthors() {
        return dslContext.selectFrom(Tables.AUTHOR).fetchInto(Author.class);
    }

    public void add(Author author) {
        dslContext.insertInto(Tables.AUTHOR, Tables.AUTHOR.NAME, Tables.AUTHOR.AGE)
                .values(author.getName(), author.getAge())
                .execute();
    }
}