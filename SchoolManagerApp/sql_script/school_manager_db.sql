CREATE DATABASE school_manager_db CHARACTER SET utf8 COLLATE utf8_general_ci;
USE school_manager_db;

CREATE TABLE student (id INT(11) AUTO_INCREMENT NOT NULL, 
`username` varchar(45) NOT NULL unique,
`password` varchar(68) NOT NULL,
first_name VARCHAR(45) default null, 
last_name VARCHAR(45) default null, 
email VARCHAR(200) default null unique, 
PRIMARY KEY(id)
);

CREATE TABLE `role` (
	id INT(11) AUTO_INCREMENT NOT NULL, 
  `student_id` int(11) DEFAULT NULL,
  `role` varchar(45) NOT NULL,
  UNIQUE KEY (`student_id`,`role`),
  FOREIGN KEY (`student_id`) REFERENCES student (`id`),
  PRIMARY KEY(id)
);

CREATE TABLE subject (
id INT(11) AUTO_INCREMENT NOT NULL, 
title VARCHAR(300) default null, 
PRIMARY KEY(id)
);

CREATE TABLE grupa (
id INT(11) AUTO_INCREMENT NOT NULL, 
description VARCHAR(300) default null, 
PRIMARY KEY(id)
);

CREATE TABLE grade (
id INT(11) AUTO_INCREMENT NOT NULL,
mark INT(11) default null,
comment VARCHAR(300) default null, 
student_id INT(11) default null, 
subject_id INT(11) default null,
PRIMARY KEY(id), 
FOREIGN KEY(student_id) REFERENCES student (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
FOREIGN KEY(subject_id) REFERENCES subject (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE grupa_student (
grupa_id INT(11) NOT NULL,
student_id INT(11) NOT NULL,
PRIMARY KEY(grupa_id, student_id),
FOREIGN KEY (grupa_id) REFERENCES grupa(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE subject_student (
subject_id INT(11) NOT NULL,
student_id INT(11) NOT NULL,
PRIMARY KEY(subject_id, student_id),
FOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

SET FOREIGN_KEY_CHECKS = 1;

insert into student (username, `password`, first_name, last_name, email) VALUES ('admin', '{noop}admin', 'Principal', 'Smith', 'smithprincipal@school.com');
insert into role (student_id, role) VALUES ('1', 'ROLE_STUDENT');
insert into role (student_id, role) VALUES ('1', 'ROLE_TEACHER');
insert into role (student_id, role) VALUES ('1', 'ROLE_ADMIN');

insert into student (username, `password`, first_name, last_name, email) VALUES ('most', '{noop}test123', 'Henryk', 'Mostowski', 'henrykmostowski@school.com');
insert into role (student_id, role) VALUES ('2', 'ROLE_STUDENT');
insert into role (student_id, role) VALUES ('2', 'ROLE_TEACHER');

insert into student (username, `password`, first_name, last_name, email) VALUES ('izbo', '{noop}test123', 'Izabela', 'Borska', 'izabelaborska@school.com');
insert into role (student_id, role) VALUES ('3', 'ROLE_STUDENT');

SELECT * FROM student;
SELECT * FROM role;
SELECT * FROM grade;
SELECT * FROM grupa;
SELECT * FROM subject order by title;
SELECT * FROM grupa_student;
SELECT * FROM subject_student;