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
    role_id bigint not null,
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

CREATE TABLE logs(
    `table`   varchar(50),
    operation varchar(35),
    `row`     bigint,
    user_id   bigint,
    message   nvarchar(200)
);

create view manager_projects as
    select p.*, m.id as manager_id from project p
        inner join user_project mp on p.id = mp.project_id
        inner join user m on mp.user_id = m.id and m.role_id = 2
    union
    select p.*, null as manager_id from project p
    where not exists(select * from user_project up
        inner join user u on up.user_id = u.id
    where project_id = p.id and u.role_id = 2);

drop view if EXISTS manager_users;
create view manager_users as
select u.id, email, first_name, second_name, role_id, calendar_id, mp.manager_id
from manager_projects mp
         inner join user_project up on mp.id = up.project_id and up.user_id != mp.manager_id
         inner join user u on up.user_id = u.id
union
select u.id, email, first_name, second_name, role_id, calendar_id, null as manager_id
from user u
where not exists(select 1 from user_project up where up.user_id = u.id)
  and u.role_id != 2;

drop view if exists vacation_details;
create view vacation_details as
    select *, get_vacation_end(v.start_date, v.length, v.user_id) as end_date from vacation v;

drop view if EXISTS current_salary;
create view current_salary as
    select * from salary order by change_date desc;

drop view if EXISTS current_position;
create view current_position as
    select up.user_id, p.name from user_position up inner join position p on up.position_id = p.id order by change_date desc;

DELIMITER //

CREATE PROCEDURE add_bonus(manager_id bigint, user bigint, project bigint, bonus double)
BEGIN
    IF (exists(select *
               from user_project u
                        INNER JOIN user_project up ON u.project_id = up.project_id AND up.user_id = manager_id
               where u.user_id = user
                 and u.project_id = project)) then
        begin
            INSERT INTO bonus(user_id, amount, `date`)
            values ((SELECT id FROM user_project up WHERE up.user_id = user_id AND up.project_id = project_id), bonus,
                    CURDATE());
            INSERT INTO logs(`table`, operation, `row`, user_id, message)
            values ('bonus', 'add_bonus', LAST_INSERT_ID(), manager_id, 'add bonus to user');
        end;
    end if;
END;

DELIMITER ;

DELIMITER //
# todo think about delayed salary and position change
CREATE PROCEDURE increase_salary(manager_id bigint, user bigint, project bigint, salary_amount double, monthly bool)
BEGIN
    IF (exists(select *
               from user_project u
                        INNER JOIN user_project up ON u.project_id = up.project_id AND up.user_id = manager_id
               where u.user_id = user
                 and u.project_id = project)) then
        begin
            INSERT INTO salary(user_id, amount, change_date, monthly)
            VALUES ((SELECT id FROM user_project up WHERE up.user_id = user AND up.project_id = project), salary_amount,
                    CURDATE(), monthly);
            INSERT INTO logs(`table`, operation, `row`, user_id, message)
            values ('salary', 'increase_salary', LAST_INSERT_ID(), manager_id, 'change user salary');
        end;
    end if;
END //

DELIMITER ;

DROP PROCEDURE IF EXISTS change_position;
DELIMITER //

CREATE PROCEDURE change_position(manager_id bigint, user bigint, position bigint)
BEGIN
    INSERT INTO user_position(user_id, position_id, change_date)
     VALUES (user, position, CURDATE());
    INSERT INTO logs(`table`, operation, `row`, message, user_id)
    values ('user_position', 'change_user_position', LAST_INSERT_ID(), 'change user position', manager_id);
END //

DELIMITER ;


DROP PROCEDURE IF EXISTS attach_project;

DELIMITER //
CREATE PROCEDURE attach_project(user_id bigint, project_id bigint)
BEGIN
    INSERT INTO user_project(user_id, project_id)
    SELECT `user`.id, project_id FROM user INNER JOIN role r on `user`.role_id = r.id WHERE r.name = 'manager';
END //

DELIMITER ;

DROP PROCEDURE IF EXISTS create_project;

DELIMITER //
CREATE PROCEDURE create_project(user_id bigint, project nvarchar(100), start_date date, budget double, OUT project_id bigint)
BEGIN
    INSERT INTO project(name, start_date, budget)
     VALUES (project, start_date, budget);
    SET project_id = LAST_INSERT_ID();
    call attach_project(user_id, project_id);
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE approve_vacation(vacation bigint, user_id bigint)
BEGIN
    update vacation v set v.approved = true, v.viewed = true where v.id = vacation;
    INSERT INTO logs(`table`, operation, `row`, user_id, message)
    values ('vacation', 'approve_vacation', vacation, user_id, 'approve user vacation');
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE reject_vacation(vacation bigint, user_id bigint)
BEGIN
    update vacation v set v.approved = false, v.viewed = true where v.id = vacation;
    INSERT INTO logs(`table`, operation, `row`, user_id, message)
    values ('vacation', 'reject_vacation', vacation, user_id, 'reject user vacation');
END //

DELIMITER ;

DROP PROCEDURE IF EXISTS finish_project;
DELIMITER //

CREATE PROCEDURE finish_project(project_id bigint, user_id bigint)
BEGIN
    DECLARE project bigint default 0;
    SELECT up.project_id INTO project
    FROM `user`
        INNER JOIN role r on `user`.role_id = r.id
        INNER JOIN user_project up on `user`.id = up.user_id WHERE up.project_id = project_id and r.name = 'manager';
    if (project <> 0) then
        begin
            update project p set p.end_date = curdate() where p.id = project;
            INSERT INTO logs(`table`, operation, `row`, user_id, message)
            values ('project', 'finish_project', project, user_id, 'finish project');
        end;
    end if;
END //

DELIMITER ;

CREATE TRIGGER project_change before UPDATE on project FOR EACH ROW begin
    insert into logs(`table`, operation, `row`, message) values ('project', 'update', old.id, 'update project');
end;

CREATE TRIGGER project_delete before DELETE on project FOR EACH ROW begin
    insert into logs(`table`, operation, `row`, message) values ('project', 'delete', old.id, 'delete project');
end;
SET GLOBAL log_bin_trust_function_creators = 1;

drop function if exists get_business_days;

DELIMITER //

CREATE FUNCTION get_business_days(date_from date, date_to date, calendar_id bigint) RETURNS INT
BEGIN
    DECLARE days INT;
    declare firstDate date;
    declare lastDate date;
    set firstDate = date_from;
    set lastDate = date_add(date_to, interval 1 day);
    SET days = 0;
    set days = 5 * (DATEDIFF(lastDate, firstDate) DIV 7) + MID('0123455401234434012332340122123401101234000123450', 7 * WEEKDAY(firstDate) + WEEKDAY(lastDate) + 1, 1);
    set days = days - (select distinct count(h.date)
                       from holiday h
                       where h.calendar_id = calendar_id
                         and h.date > firstDate
                         AND h.date < lastDate
                         and not (DAYOFWEEK(h.date) = 7
                           or DAYOFWEEK(h.date) = 1));
    set days = days + (select distinct count(h.transfer_date)
                       from holiday h
                       where h.calendar_id = calendar_id
                         and h.transfer_date is not null
                         and h.transfer_date > firstDate
                         AND h.transfer_date < lastDate
                         and (DAYOFWEEK(h.date) = 7
                           or DAYOFWEEK(h.date) = 1));
    RETURN days;
END; //
DELIMITER ;

drop function if exists get_working_days;

DELIMITER //

CREATE FUNCTION get_working_days(date date, calendar_id bigint) RETURNS INT
BEGIN
    DECLARE days INT;
    declare firstDate date;
    declare lastDate date;
    set firstDate = date_add(date_add(LAST_DAY(date),interval 1 DAY), interval -1 MONTH);
    set lastDate = LAST_DAY(date);
    SET days = (select get_business_days(firstDate, lastDate, calendar_id));
    RETURN days;
END; //
DELIMITER ;

drop function if exists get_project_salary;

DELIMITER //

CREATE FUNCTION get_project_salary(date date, user_project bigint, monthly boolean) RETURNS double
BEGIN
    return (select ls.amount from (select * from current_salary s where s.change_date <= date and s.user_id = user_project limit 1)
    as ls  where ls.monthly = monthly);
END; //
DELIMITER ;

drop function if exists get_month_salary;

DELIMITER //

CREATE FUNCTION get_month_salary(date date, user bigint) RETURNS double
BEGIN
    DECLARE salary double;
    DECLARE project_count int;
    DECLARE hours_per_project double;
    set salary = ifnull((select sum(IFNULL(get_project_salary(date, up.id, true), 0))
    from user_project up
        inner join project p on p.id = up.project_id
    where up.user_id = user
      and p.start_date >= date and (p.end_date is null or p.end_date <= date)), 0);
    set project_count = (select distinct count(*) from user_project up where up.user_id = user);
    if (project_count > 0) then
        set hours_per_project = (select get_working_days(date, u.calendar_id) * 8 / project_count from user u where u.id = user);
        set salary = salary + (select sum(IFNULL(get_project_salary(date, up.id, false) * hours_per_project, 0))
                               from user_project up
                                        inner join project p on p.id = up.project_id
                               where up.user_id = user and  p.start_date >= date and (p.end_date is null or p.end_date <= date));
    end if;
    return salary;
END; //
DELIMITER ;

drop function if exists get_real_salary;
CREATE FUNCTION get_real_salary(date date, user bigint) RETURNS double
BEGIN
    DECLARE salary double;
    set salary = (select sum(s.amount * r.time / 60) from report r
        inner join task t on t.id = r.task_id
        inner join user_project up on t.user_id = up.id
        inner join salary s on up.id = s.user_id
--         inner join user_project up1 on up1.user_id = t.user_id and up1.project_id = up.project_id
    where t.user_id = user and LAST_DAY(date) = LAST_DAY(r.date)
    and not s.monthly);
--     todo add check for finished poject
    set salary = salary + (select sum(s.amount)
    from user_project up
             inner join project p on p.id = up.project_id
             inner join salary s on up.id = s.user_id
    where up.user_id = user
      and s.monthly);
    return salary;
END; //
DELIMITER ;


drop function if exists get_position;
CREATE FUNCTION get_position(date date, user bigint) RETURNS bigint
BEGIN
    return (select p.id from user_position up join position p on p.id = up.position_id where up.user_id = user and up.change_date <= date limit 1);
END; //
DELIMITER ;

drop function if exists get_vacation_end;

DELIMITER //

CREATE FUNCTION get_vacation_end(date date, length bigint, user bigint) RETURNS date
BEGIN
    DECLARE days INT;
    DECLARE calendar_id bigint;
    declare firstDate date;
    declare lastDate date;
    set calendar_id = (select calendar_id from user u where u.id = user);
    SET days = length;
    set days = days + (select distinct count(h.date)
                       from holiday h
                       where h.calendar_id = calendar_id
                         and h.date > firstDate
                         AND h.date < lastDate
                         and not (DAYOFWEEK(h.date) = 7
                           or DAYOFWEEK(h.date) = 1));
    set days = days - (select distinct count(h.transfer_date)
                       from holiday h
                       where h.calendar_id = calendar_id
                         and h.transfer_date is not null
                         and h.transfer_date > firstDate
                         AND h.transfer_date < lastDate
                         and (DAYOFWEEK(h.date) = 7
                           or DAYOFWEEK(h.date) = 1));
    RETURN date_add(date, interval days day);
END; //
DELIMITER ;

