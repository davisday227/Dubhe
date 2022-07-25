CREATE TABLE customer
(
    id int not null auto_increment primary key,
    name varchar(255),
    age int
);

CREATE TABLE author
(
    id int not null auto_increment primary key,
    name varchar(255),
    age int
);

CREATE TABLE book
(
    id int not null auto_increment primary key,
    name varchar(255),
    author int
);