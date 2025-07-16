select substr(p.name, 1, 1),count(substr(p.name, 1, 1)) 人数
from tb_emp p
group by substr(p.name, 1, 1);