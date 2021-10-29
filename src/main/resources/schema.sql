CREATE TABLE USER(
    id integer not null auto_increment,
    account varchar(128) not null, unique,
    password varchar(128) not null,
    name varchar(128) not null,
    address varchar(128) not null,
    phone_no varchar(16) not null,
    email varchar(128) not null,
    birth date not null,
    primary key (id)
);

create table item(
    id integer not null auto_increment,
    name varchar(128) not null,
    price integer not null
);

create table cart(
    id integer not null auto_increment,
    name varchar(128) not null,
    add_date date not null
);