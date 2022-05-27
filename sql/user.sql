-- UserRepository

desc user;

select * from user;

insert into user values(null, '관리자', 'admin@mysite.com', '1234', 'male', now());


delete from user where no > 2;

-- login
select no, name
 from user where email ='city@city.city' and password='3333';
 
 -- findByNo
 select no, name, email, gender
 from user
 where name = 'city';
 
 update user set name='city',gender='male' where no =5;