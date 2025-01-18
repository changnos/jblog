show tables;	
select * from user;
delete from user where name = '1';
-- insert into user values ('1', '1', '1');
-- insert into user values ('123', '123', '123');
select * from blog;
-- insert into blog values ('test', 'test', 'test');
-- insert into blog values ('123', 'default', 'Default Title');
select * from category;
select * from category where blog_id='1';
-- insert into category values (null, 'Default Name', 'This is default category.', '123');
-- insert into category values (null, '카테고리1', '1111 게시판', 'test');
-- insert into category values (null, '카테고리2', '2222 게시판', 'test');

desc post;
select * from post;

select * from category;
select name, (select count(*) from post where category_id=c.id) as postCnt, description from category c group by id;

-- insert into post values(null, 'testtitle', 'testcontents', now(), 1);
-- insert into post values(null, 'testtitle222', 'testcontents222', now(), 1);
-- insert into post values(null, 'testt3333', '333333333333333 3333', now(), 1);

-- insert into post values(null, '22testtitle', 'testcontents', now(), 2);
-- insert into post values(null, '22testtitle222', 'testcontents222', now(), 2);
-- insert into post values(null, '22testt3333', '333333333333333 3333', now(), 2);