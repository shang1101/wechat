create table user_location(
id int not null auto_increment primary key,
open_id varchar(50) not null,
lng varchar(30) not null,
lat varchar(30) not null,
bd09_lng varchar(30),
bd09_lat varchar(30)
);