create database time_tracker;
use time_tracker;

CREATE TABLE role(
    id bigint primary key auto_increment,
    name nvarchar(20) not null
);

CREATE TABLE calendar(
                         id bigint primary key auto_increment,
                         locale nvarchar(20) not null
);

CREATE TABLE holiday(
    id bigint primary key auto_increment,
    date date not null,
    transfer_date date,
    calendar_id bigint,
    foreign key (calendar_id) references calendar(id)
);

CREATE TABLE user(
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

CREATE TABLE project(
    id bigint primary key auto_increment,
    name nvarchar(100) not null,
    start_date date not null,
    end_date date
);

CREATE TABLE user_project(
    id bigint primary key auto_increment,
    user_id bigint,
    project_id bigint,
    foreign key (user_id) references user(id),
    foreign key (project_id) references project(id)
);

CREATE TABLE bonus(
    id bigint primary key auto_increment,
    user_id bigint,
    amount double not null,
    date date not null,
    foreign key (user_id) references user_project(id)
);

CREATE TABLE salary(
    id bigint primary key auto_increment,
    user_id bigint,
    amount double not null,
    change_date date not null,
    monthly boolean,
    foreign key (user_id) references user_project(id)
);

CREATE TABLE task(
    id bigint primary key auto_increment,
    name varchar(200) not null,
    start_date date,
    end_date date,
    user_id bigint,
    urgency int,
    complexity int,
    foreign key (user_id) references user_project(id)
);

CREATE TABLE report(
    id bigint primary key auto_increment,
    text varchar(200) not null,
    date date not null,
    time bigint not null,
    task_id bigint,
    foreign key (task_id) references task(id)
);

CREATE TABLE customer(
  id bigint primary key auto_increment,
  name varchar(200) not null unique,
  contact varchar(200)
);

CREATE TABLE customer_project(
    customer_id bigint,
    project_id bigint,
    primary key (customer_id, project_id),
    foreign key (customer_id) references customer(id),
    foreign key (project_id) references project(id)
);

CREATE TABLE `position`(
                         id bigint primary key auto_increment,
                         name varchar(100) not null unique
);

CREATE TABLE user_position(
  user_id bigint,
  position_id bigint,
  change_date date,
  primary key (user_id, position_id, change_date),
  foreign key (user_id) references user(id),
  foreign key (position_id) references `position`(id)
);

CREATE TABLE vacation(
  id bigint primary key auto_increment,
  start_date date not null,
  length int not null,
  approved boolean default false,
  viewed boolean default false,
  user_id bigint,
  foreign key (user_id) references user(id)
);

# create view manager_projects as
#     select * from project
#         inner join user_project mp on project.id = mp.project_id
#         inner join user m on mp.user_id = m.id and m.role_id = 2
#     union
#     select * from project where not exists(select * from user_project
#         inner join user u on user_project.user_id = u.id
#     where project_id = project.id and u.role_id = 2);
#
# create view manager_users as
#     select * from project
#         inner join user_project mp on project.id = mp.project_id
#         inner join user m on mp.user_id = m.id and m.role_id = 2
#         inner join user_project up on project.id = up.project_id
#         inner join user u on up.user_id = u.id and u.role_id != 2
#         inner join salary s on up.id = s.user_id
#         inner join bonus b on up.id = b.user_id;
#
# create view project_vacations as
#     select * from vacation where ;

insert into role(name) values ('admin'), ('manager'), ('user');

insert into user(first_name, second_name, email, password)
values ('admin', 'admin', 'admin@company.com', '$2a$10$EFEJzuZA3DLrNeB8bIWv9eviqKs05bBkKe3ZQfBn8itQukd5jDFH2');-- password = admin

insert into customer(name, contact)
values ('Internal', 'ceo@company.com');-- password = admin
# insert INTO user_role (user_id, role_id)
# values ((select id from "user" where email = 'admin@dima'), (select id from role where name = 'admin'));
