/*
 * This file is generated by jOOQ.
 */
package com.dubhe.jooq.model;


import com.dubhe.jooq.model.tables.Book;
import com.dubhe.jooq.model.tables.Customer;
import com.dubhe.jooq.model.tables.records.BookRecord;
import com.dubhe.jooq.model.tables.records.CustomerRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * jooq.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<BookRecord> KEY_BOOK_PRIMARY = Internal.createUniqueKey(Book.BOOK, DSL.name("KEY_book_PRIMARY"), new TableField[] { Book.BOOK.ID }, true);
    public static final UniqueKey<CustomerRecord> KEY_CUSTOMER_PRIMARY = Internal.createUniqueKey(Customer.CUSTOMER, DSL.name("KEY_customer_PRIMARY"), new TableField[] { Customer.CUSTOMER.ID }, true);
}
