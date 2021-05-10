insert into time_tracker.role(name) values ('admin'), ('manager'), ('user');
insert into time_tracker.calendar(locale) values ('be_BY');

insert into time_tracker.user(first_name, second_name, email, password, role_id, calendar_id)
values ('admin', 'admin', 'admin@company.com', '$2a$10$EFEJzuZA3DLrNeB8bIWv9eviqKs05bBkKe3ZQfBn8itQukd5jDFH2', 1, 1);-- password = admin

insert into time_tracker.customer(name, contact)
values ('Internal', 'ceo@company.com');

INSERT INTO time_tracker.user (id, email, password, first_name, second_name, role_id, calendar_id)
VALUES (2, 'employee1@company.com', '$2a$10$H3wKhq0913mlPMKF0P5X4OZkZ2qYcpFMUHs7m2VbVrng5M5fnQhwa', 'employee', 'employee', 3, 1);
INSERT INTO time_tracker.user (id, email, password, first_name, second_name, role_id, calendar_id)
VALUES (3, 'manager@company.com', '$2a$10$4SiSVmLTv8GBd01TEL6Oy.ORzk9UVN8ojWO5uXiMRZzCGSTiR0yH2', 'manager', 'manager', 2, 1);
