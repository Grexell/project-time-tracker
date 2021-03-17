create table role(
    id bigint primary key auto_increment,
    name nvarchar(20) not null
);

create table holiday(
    id bigint primary key auto_increment,
    date date not null,
    transfer_date date,
    calendar_id bigint,
    foreign key (calendar_id) references calendar(id)
);

create table calendar(
    id bigint primary key auto_increment,
    locale nvarchar(20) not null
);

create table user(
    id bigint primary key auto_increment,
    email nvarchar(100) not null UNIQUE,
    password nvarchar(100) not null,
    first_name nvarchar(100) not null,
    second_name nvarchar(100) not null,
    role_id bigint,
    calendar_id bigint,
    foreign key (role_id) references role(id),
    foreign key (calendar_id) references calendar(id)
);

create table project(
    id bigint primary key auto_increment,
    name nvarchar(100) not null
);

create table user_project(
    id bigint primary key auto_increment,
    user_id bigint,
    project_id bigint,
    foreign key (user_id) references user(id),
    foreign key (project_id) references project(id)
);

create table bonus(
    id bigint primary key auto_increment,
    user_id bigint,
    amount double not null,
    date date not null,
    foreign key (user_id) references user_project(id)
);

create table salary(
    id bigint primary key auto_increment,
    user_id bigint,
    amount double not null,
    change_date date not null,
    monthly boolean,
    foreign key (user_id) references user_project(id)
);

create table task(
    id bigint primary key auto_increment,
    name varchar(200) not null,
    start_date date,
    end_date date,
    user_id bigint,
    foreign key (user_id) references user_project(id)
);

create table report(
    id bigint primary key auto_increment,
    message varchar(200) not null,
    date date
);

create table customer(
  id bigint primary key auto_increment,
  name varchar(200) not null unique
);

create table customer_project(
    customer_id bigint,
    project_id bigint,
    primary key (customer_id, project_id),
    foreign key (customer_id) references customer(id),
    foreign key (project_id) references project(id)
);

create table user_position(
  user_id bigint,
  position_id bigint,
  change_date date,
  primary key (user_id, position_id, change_date),
  foreign key (user_id) references user(id),
  foreign key (position_id) references position(id)
);

create table position(
  id bigint primary key auto_increment,
  name varchar(100) not null unique
);

create table vacation(
  id bigint primary key auto_increment,
  start_date date not null,
  length int not null,
  approved boolean default false,
  rejected boolean default false,
  user_id bigint,
  foreign key (user_id) references user(id)
);

insert into role(name) values ('admin'), ('manager'), ('user');

insert into user(first_name, second_name, email, password)
values ('admin', 'admin', 'admin@dima', '$2a$10$EFEJzuZA3DLrNeB8bIWv9eviqKs05bBkKe3ZQfBn8itQukd5jDFH2');-- password = admin
# insert INTO user_role (user_id, role_id)
# values ((select id from "user" where email = 'admin@dima'), (select id from role where name = 'admin'));
