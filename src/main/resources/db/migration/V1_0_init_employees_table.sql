CREATE TABLE employee
(
    employee_id SERIAL        not null,
    name        VARCHAR(20)   not null,
    surname     VARCHAR(20)   not null,
    salary      numeric(7, 2) not null,
    phone       VARCHAR(32)   not null,
    email       VARCHAR(32)   not null,
    PRIMARY KEY (employee_id)


);