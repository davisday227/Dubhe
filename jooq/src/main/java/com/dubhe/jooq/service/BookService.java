package com.dubhe.jooq.service;

import com.dubhe.jooq.model.Tables;
import com.dubhe.jooq.model.tables.pojos.Author;
import com.dubhe.jooq.model.tables.pojos.Book;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
    private static Logger log = LoggerFactory.getLogger(BookService.class);

    @Autowired
    DSLContext dslContext;

    public List<Book> getBooks() {
        return dslContext.select(Tables.BOOK.ID, Tables.BOOK.NAME, Tables.AUTHOR.NAME.as(Tables.BOOK.AUTHOR))
                .from(Tables.BOOK)
                .join(Tables.AUTHOR)
                .on(Tables.BOOK.AUTHOR.eq(Tables.AUTHOR.ID))
                .fetchInto(Book.class);
    }

    @Transactional
    public void add(Book book) {
        Author author = dslContext.selectFrom(Tables.AUTHOR)
                .where(Tables.AUTHOR.ID.eq(Integer.valueOf(book.getAuthor())))
                .fetchAnyInto(Author.class);

        if (author == null) {
            log.info("Can not get author: {}", book.getAuthor());
            throw new RuntimeException("Author not exist");
        }

        dslContext.insertInto(Tables.BOOK, Tables.BOOK.NAME, Tables.BOOK.AUTHOR)
                .values(book.getName(), book.getAuthor())
                .execute();
    }
}