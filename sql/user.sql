-- UserRepository

desc user;

select * from user;

insert into user values(null, '관리자', 'admin@mysite.com', '1234', 'male', now());


delete from user where no > 2;