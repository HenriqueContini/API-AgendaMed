create table if not exists `appointment` (
	id int not null auto_increment,
	date timestamp not null,
	doctor int,
	patient int,
	status int,
    primary key (id),
    constraint FK_appointmentDoctor foreign key (doctor) references doctor(id),
    constraint FK_appointmentUser foreign key (patient) references users(id)
);