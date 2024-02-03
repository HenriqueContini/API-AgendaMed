create table if not exists `users` (
	id int not null auto_increment,
    name varchar(150) not null,
    created_at timestamp not null,
    primary key (id)
);