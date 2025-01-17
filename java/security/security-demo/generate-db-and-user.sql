create database `security-demo`;
use `security-demo`;

CREATE TABLE `user`(
	`id` int not null auto_increment primary key,
    `username` varchar(50) default null,
    `password` varchar(500) default null,
    `enabled` boolean not null
);

create unique index `user_username_uindex` on `user`(`username`);

insert into `user` (`username`, `password`, `enabled`) values
('admin','{bcrypt}$2a$12$oTzHFvbJXGPzcfipC73ONe79PFZquX7EfxMrwrgHzQr639AZCGHgq', true);

insert into `user` (`username`, `password`, `enabled`) values
('boyan','{bcrypt}$2a$10$fuuSFbZzpNjU5IqrTndyveZMgcW2LYeV8Pq7z/F288N5iAkGfOoT.', true);