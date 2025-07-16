--
use db01;

-- MYSQL 默认 REPEATABLE-READ
select @@transaction_isolation;

-- 脏读, 读到其他事务未提交的数据(Insert Update Delete)
-- 虚读(不可重复读), 读到其他事务已提交的更新(Update)
-- 幻读, 读到其他事务已提交的增删操作

-- set session transaction isolation level read uncommitted ;

-- set session transaction isolation level read committed;

set session transaction isolation level repeatable read;

start transaction;


select *
from emp
where id = 2;

select count(*)
from emp;



rollback;

commit;