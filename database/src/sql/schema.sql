create table "user"(
    id SERIAL primary key,
    username varchar not null UNIQUE,
    password varchar not null
);

create table role(
    id SERIAL primary key,
    name varchar not null
);

create table user_role(
    user_id bigint not null,
    role_id bigint not null,
    primary key(user_id, role_id),
    foreign key (user_id) references "user"(id) on delete cascade,
    foreign key (role_id) references "role"(id) on delete cascade
);

create table project(
    id SERIAL primary key,
    name varchar not null
);

create table task(
    id SERIAL primary key,
    name varchar not null,
    start_date date,
    end_date date,
    user_id bigint
);

create table report(
    id SERIAL primary key,
    message varchar not null,
    date date
);

create table customer(
  id SERIAL primary key,
  name varchar not null unique
);

create table position(
  id SERIAL primary key,
  name varchar not null unique
);

insert into role(name) values ('admin'), ('manager'), ('user');

insert into "user"(username, password)  values ('admin', '$2a$10$EFEJzuZA3DLrNeB8bIWv9eviqKs05bBkKe3ZQfBn8itQukd5jDFH2');-- password = admin
insert INTO user_role (user_id, role_id)
 values ((select id from "user" where username = 'admin'), (select id from role where name = 'admin'));
