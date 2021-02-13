-- schema creation
create database coupon_system;

-- tables creation

create table COMPANIES(
id int auto_increment,
`name` varchar(50),
email varchar(50),
`password` varchar(50),
primary key(id)
);

create table CUSTOMERS(
id int auto_increment,
first_name varchar(20),
last_name varchar(20),
email varchar(50),
`password` varchar(50),
primary key(id)
);

create table COUPONS_CATEGORIES(
id int auto_increment,
category_name varchar (20),
primary key(id)
);

create table COUPONS(
id int auto_increment,
company_id int, 
category_id int,
coupon_title varchar (20),
coupon_description varchar (200),
coupon_creation_date date,
coupon_expiration_date date,
coupons_amount int,
coupon_price float,
coupon_image varchar (100),
primary key(id),
foreign key(company_id) references COMPANIES(id),
foreign key(category_id) references COUPONS_CATEGORIES(id)
);

create table CUSTOMERS_VS_COUPONS(
customer_id int,
coupon_id int,
primary key(customer_id, coupon_id),
foreign key(customer_id) references CUSTOMERS(id),
foreign key(coupon_id) references COUPONS(id)
);


