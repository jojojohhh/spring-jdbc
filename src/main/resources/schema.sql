drop table if exists user;
drop table if exists product;
drop table if exists cart;
drop table if exists `order`;
drop table if exists order_product;
drop table if exists review;

create table user(
    id long not null auto_increment,
    account varchar(255) not null unique,
    password varchar(128) not null,
    name varchar(128) not null,
    address varchar(255) not null,
    phone_no varchar(16) not null,
    email varchar(128) not null,
    birth date not null,
    primary key (id)
);

create table product(
    id long not null auto_increment,
    name varchar(128) not null,
    price integer not null,
    stock integer not null,
    primary key (id)
);

create table cart(
    id long not null auto_increment,
    user_id long,
    product_id long,
    product_quantity integer default 1,
    primary key (id),
    foreign key (user_id) references user (id),
    foreign key (product_id) references product (id)
);

create table `order`(
  id long not null auto_increment,
  user_id long not null,
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
    id long not null auto_increment,
    order_id long,
    product_id long,
    product_quantity integer default 1,
    primary key (id),
    foreign key (order_id) references `order` (id),
    foreign key (product_id) references product (id)
);

create table review(
    id long not null auto_increment,
    order_id long,
    product_id long,
    title varchar(255),
    content varchar(5000),
    date date,
    `like` integer default 0,
    primary key (id),
    foreign key (order_id) references `order` (id),
    foreign key (product_id) references product (id)
);

insert into user (account, password, name, address, phone_no, email, birth) values ('josangjea', '1234', '조상제', '부산광역시 남구 대연동', '01012345678', 'qwhtkdwp@gmail.com', '1996-07-20');
insert into product (name, price, stock) values ('운동화', 200000, 10);
insert into cart (user_id, product_id) values (1, 1);
insert into `order` (user_id, amount, shipping_address, recipient, recipient_phone, delivery_charge, order_date) values (1, 200000, '부산광역시 남구 대연동', '조상제', '01012345678', 0, '2021-10-31');
insert into order_product (order_id, product_id) values (1, 1);
insert into review (order_id, product_id, title, content, date) values (1, 1, '리뷰제목', '리뷰내용', '2021-10-31');
