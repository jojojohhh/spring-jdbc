create table user(
    id integer not null auto_increment,
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
    id integer not null auto_increment,
    name varchar(128) not null,
    price integer not null,
    stock integer not null,
    primary key (id)
);

create table cart(
    id integer not null auto_increment,
    user_id integer,
    product_id integer,
    product_quantity integer default 1,
    primary key (id),
    foreign key (user_id) references user (id),
    foreign key (product_id) references product (id)
);

create table order(
  id integer not null auto_increment,
  user_id integer not null,
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
    id integer not null auto_increment,
    order_id integer,
    product_id integer,
    product_quantity integer default 1,
    primary key (id),
    foreign key (order_id) references order (id),
    foreign key (product_id) references product (id)
);

create table review(
    id integer not null auto_increment,
    order_id integer,
    product_id integer,
    title varchar(255),
    content varchar(5000),
    date date,
    like integer default 0,
    primary key (id),
    foreign key (order_id) references order (id),
    foreign key (product_id) references product (id)
);