CREATE TABLE customer
(
    id varchar(36) primary key,
    uri varchar(255),
    predicates varchar(255),
    filters varchar(255)
);

CREATE TABLE `gateway_define` (
  `id` varchar(36) not null,
  `uri` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `predicates` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `filters` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  timeout int,
  method varchar(8),
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

insert into gateway_define (id, uri, predicates, timeout, method) values ("1", "http://localhost:8091", "Path:/books/**", 12, 'POST');