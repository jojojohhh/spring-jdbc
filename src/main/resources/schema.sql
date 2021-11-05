drop table if exists review;
drop table if exists order_product;
drop table if exists `order`;
drop table if exists cart;
drop table if exists product_category;
drop table if exists product;
drop table if exists user;

create table user(
    id bigint not null auto_increment,
    account varchar(255) not null unique,
    password varchar(128) not null,
    name varchar(128) not null,
    address varchar(255) not null,
    phone_no varchar(16) not null,
    email varchar(128) not null,
    birth date,
    create_at datetime default now(),
    password_update_at datetime default now(),
    role enum('ROLE_USER', 'ROLE_ADMIN'),
    primary key (id)
);

create table product_category(
  id bigint not null,
  name varchar(128) not null,
  parent bigint,
  primary key (id),
  foreign key (parent) references product_category(id)
);

create table product(
    id bigint not null auto_increment,
    name varchar(128) not null,
    category_id bigint not null,
    price integer not null,
    stock integer not null,
    primary key (id),
    foreign key (category_id) references product_category(id)
);

create table cart(
    id bigint not null auto_increment,
    user_id bigint,
    product_id bigint,
    product_quantity integer default 1,
    primary key (id),
    foreign key (user_id) references user (id),
    foreign key (product_id) references product (id)
);

create table `order`(
  id bigint not null auto_increment,
  user_id bigint not null,
  amount integer,
  shipping_address varchar(255),
  recipient varchar(100),
  recipient_phone varchar(16),
  delivery_charge integer,
  order_date date,
  primary key (id),
  foreign key (user_id) references user (id)
);

create table order_product(
    id bigint not null auto_increment,
    order_id bigint,
    product_id bigint,
    product_quantity integer default 1,
    primary key (id),
    foreign key (order_id) references `order` (id),
    foreign key (product_id) references product (id)
);

create table review(
    id bigint not null auto_increment,
    order_id bigint,
    product_id bigint,
    title varchar(255),
    content varchar(5000),
    date date,
    `like` integer default 0,
    primary key (id),
    foreign key (order_id) references `order` (id),
    foreign key (product_id) references product (id)
);

insert into user (account, password, name, address, phone_no, email, birth, role) values ('josangjea', '1234', '조상제', '부산광역시 남구 대연동', '01012345678', 'qwhtkdwp@gmail.com', '1996-07-20', 'ROLE_USER');
insert into product_category(id, name) values (10, 'CLOTHES');
insert into product_category(id, name, parent) values (11, 'TOP', 10);
insert into product_category(id, name, parent) values (12, 'PANTS', 10);
insert into product_category(id, name, parent) values (13, 'OUTER', 10);
insert into product_category(id, name, parent) values (14, 'SHOES', 10);
insert into product_category(id, name, parent) values (15, 'BAG', 10);
insert into product_category(id, name, parent) values (16, 'ACCESSORY', 10);
insert into product_category(id, name, parent) values (17, 'UNDERWEAR', 10);
insert into product_category(id, name, parent) values (18, 'NEW_ARRIVAL', 10);
insert into product (name, category_id, price, stock) values ('운동화', 14, 200000, 10);
insert into cart (user_id, product_id) values (1, 1);
insert into `order` (user_id, amount, shipping_address, recipient, recipient_phone, delivery_charge, order_date) values (1, 200000, '부산광역시 남구 대연동', '조상제', '01012345678', 0, '2021-10-31');
insert into order_product (order_id, product_id) values (1, 1);
insert into review (order_id, product_id, title, content, date) values (1, 1, '리뷰제목', '리뷰내용', '2021-10-31');
