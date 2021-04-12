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
    end_date date,
    budget double not null
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

DELIMITER //

CREATE PROCEDURE add_bonus(user_id, bonus)
BEGIN
INSERT INTO bonus(user_id, amount, "date")
values ((SELECT id FROM user_project up WHERE up.user_id = user_id AND up.project_id = project_id), bonus, CURDATE())
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE increase_salary(user_id, project_id, salary_amount, monthly)
BEGIN
    INSERT INTO salary(user_id, amount, change_date, monthly)
     VALUES ((SELECT id FROM user_project up WHERE up.user_id = user_id AND up.project_id = project_id), salary_amount, CURDATE(), monthly)
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE change_position(user_id, position_id)
BEGIN
    INSERT INTO user_position(user_id, position_id, change_date)
     VALUES (user_id, position_id, CURDATE());
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE create_project(user_id, project, budget, project_id OUT)
BEGIN
    INSERT INTO project(name, start_date, budget)
     VALUES (user_id, project, budget);
    SET project_id = LAST_INSERT_ID();
    INSERT INTO user_project(user_id, project_id)
     SELECT id, project_id FROM user INNER JOIN role r on "user".role_id = r.id WHERE r.name = 'manager';
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE approve_vacation(vacation, user_id)
BEGIN

END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE reject_vacation(vacation, user_id)
BEGIN

END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE finish_project(project_id, user_id)
BEGIN
    DECLARE project bigint default 0;
    SELECT up.project_id INTO project
    FROM user
        INNER JOIN role r on "user".role_id = r.id
        INNER JOIN user_project up on "user".id = up.user_id WHERE r.name = 'manager'
END //

DELIMITER ;