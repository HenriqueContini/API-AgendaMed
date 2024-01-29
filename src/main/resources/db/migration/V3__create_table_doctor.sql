create table if not exists `doctor` (
	id int not null auto_increment,
    name varchar(150) not null,
    specialization int,
    start_time time,
    finish_time time,
    primary key (id),
    constraint FK_doctorSpecialization foreign key (specialization) references specialization(id)
);