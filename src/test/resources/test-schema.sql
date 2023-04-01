drop table if exists jobs cascade;
create table jobs
(
    job_id     varchar(10)         not null
        primary key,
    job_title  varchar(35)         not null,
    min_salary decimal(8)  null,
    max_salary decimal(8)  null
);

drop table if exists regions cascade;
create table regions
(
    region_id   int  not null
        primary key,
    region_name varchar(25)  null
);

drop table if exists countries cascade;
create table countries
(
    country_id   char(2)      not null
        primary key,
    country_name varchar(40)  null,
    region_id    int  not null,
    constraint countries_ibfk_1
        foreign key (region_id) references regions (region_id)
);

drop table if exists locations cascade;
create table locations
(
    location_id    int  auto_increment
        primary key,
    street_address varchar(40) null,
    postal_code    varchar(12) null,
    city           varchar(30) not null,
    state_province varchar(25) null,
    country_id     char(2)     not null,
    constraint locations_ibfk_1
        foreign key (country_id) references countries (country_id)
);

drop table if exists departments cascade;
create table departments
(
    department_id   int  not null
        primary key,
    department_name varchar(30)  not null,
    manager_id      int  null,
    location_id     int  null,
    constraint departments_ibfk_1
        foreign key (location_id) references locations (location_id)
);

drop table if exists employees cascade;
create table employees
(
    employee_id    int   not null
        primary key,
    first_name     varchar(20)   null,
    last_name      varchar(25)   not null,
    email          varchar(25)   not null,
    phone_number   varchar(20)   null,
    hire_date      date          not null,
    job_id         varchar(10)   not null,
    salary         decimal(8, 2) not null,
    commission_pct decimal(2, 2) null,
    manager_id     int   null,
    department_id  int   null,
    constraint employees_ibfk_1
        foreign key (job_id) references jobs (job_id),
    constraint employees_ibfk_2
        foreign key (department_id) references departments (department_id),
    constraint employees_ibfk_3
        foreign key (manager_id) references employees (employee_id)
);

alter table departments
    add constraint departments_ibfk_2
        foreign key (manager_id) references employees (employee_id);


drop table if exists job_history cascade;
create table job_history
(
    employee_id   int  not null,
    start_date    date         not null,
    end_date      date         not null,
    job_id        varchar(10)  not null,
    department_id int  not null,
    constraint employee_id
        unique (employee_id, start_date),
    constraint job_history_ibfk_1
        foreign key (employee_id) references employees (employee_id),
    constraint job_history_ibfk_2
        foreign key (job_id) references jobs (job_id),
    constraint job_history_ibfk_3
        foreign key (department_id) references departments (department_id)
);


