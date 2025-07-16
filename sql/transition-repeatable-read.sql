use db01;

-- Mysql 默认隔离级别 Repeatable Read
start transaction;

-- 更新
update emp
set name = '宋江222'
where id = 2;

-- 插入
insert into emp(id, username, password, name, gender, phone)
values (null, 'lisi', '123456', '李四', 1, '18754281928');

-- 删除
delete from emp where id = 2;

select count(*) from emp;

select last_insert_id();

-- 回滚
rollback;

-- 提交
commit;