drop table if exists product CASCADE;
drop table if exists user CASCADE;
drop table if exists user_order CASCADE;
drop table if exists user_favorite CASCADE;
create table product (product_id integer not null, product_name varchar(255), on_sale boolean, primary key (product_id));
create table user (user_id integer not null, pass_word varchar(255), user_name varchar(255), primary key (user_id));
create table user_order (order_id integer not null,user_id integer, product_id integer, rating integer,  primary key (order_id));
create table user_favorite (fav_id integer not null,user_id integer, product_id integer, primary key (fav_id));