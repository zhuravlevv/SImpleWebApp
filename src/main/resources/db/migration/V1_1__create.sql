drop table if exists employee;

create table employee (
    employee_id  serial not null,
    date_of_birth date not null,
    department_id int4 not null check (department_id>=1),
    first_name varchar(255) not null,
    gender int4 not null,
    job_title varchar(30) not null,
    last_name varchar(255) not null,
    primary key (employee_id)
)
