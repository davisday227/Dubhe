create table users (
    id int unsigned not null AUTO_INCREMENT comment '主键ID',
    name varchar(12) not null comment '姓名',
    age int unsigned comment '年龄',
    sexy varchar(1) comment '性别',
    primary key (id)
)
DEFAULT CHARSET=utf8mb4
DEFAULT COLLATE=utf8mb4_bin
AUTO_INCREMENT=0
COMMENT='用户信息表';

-- 查看表注释

select table_name,table_comment
from information_schema.TABLES
where table_name = 'users' and table_schema='gen';

-- 查看字段信息
select column_name,data_type,column_comment,numeric_precision, numeric_scale,character_maximum_length,is_nullable nullable
from information_schema.columns
where table_name = 'users';

-- 查看主键
select column_name from information_schema.key_column_usage where table_name = 'users' and table_schema = 'gen';