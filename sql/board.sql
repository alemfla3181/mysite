-- board 

desc board;

select b.no, a.name, b.title, b.contents,b.hit,b.reg_date, b.g_no, b.o_no, b.depth, b.user_no 
 from user a, board b
 where a.no = b.user_no  order by g_no desc, o_no asc limit 0, 5;

select b.no, a.name, b.title, b.contents,b.hit,b.reg_date, b.g_no, b.o_no, b.depth, b.user_no
 from user a, board b 
 where a.no = b.user_no
 and (a.name like concat('%zz%')
 or b.title like concat('%zz%'))
 order by g_no desc, o_no asc
 limit 0,5;
 
select * from user;
insert into board(no,title,contents,hit,g_no,o_no,depth,user_no)
 values(null,"ㅋㅋ","케켘",0,(select max(g_no) from board a)+1,1,1,2);


select max(o_no)
 from board where g_no = (select g_no from board where no = 9);

update board set title="Hello", contents="fsdaafsfdasfsdafsd" where no = 5; 

select max(g_no) from board;


select no, title, contents as content, hit, reg_date, g_no, o_no, depth, user_no from board where no = 121;
select *  from board;

update board set o_no= o_no+1 where g_no = 4 and o_no > 5;
