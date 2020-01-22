create table if not exists "table_user"
(
    id serial not null
        constraint user_pk
            primary key,
    name varchar(255) null,
    password varchar(255) not null,
    username varchar(90) not null
);