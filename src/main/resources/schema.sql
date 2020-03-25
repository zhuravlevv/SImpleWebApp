DROP TABLE employee;

CREATE TABLE employee
(
    employee_id SERIAL,
    first_name varchar(255),
    last_name varchar(255),
    department_id integer,
    job_title varchar(255),
    gender varchar(15),
    date_of_birth date
);