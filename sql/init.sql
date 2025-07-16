drop database if exists `manage_system`;

create database if not exists `manage_system`;

use `manage_system`;

drop table if exists tb_dept;

create table if not exists tb_dept
(
    `id`          bigint primary key auto_increment comment '部门编号',
    `name`        varchar(20) not null unique comment '部门名称',
    `create_time` datetime    not null default now() comment '创建时间',
    `update_time` datetime    not null comment '更新时间',
    `status`      tinyint     not null default 1 comment '部门状态 0:未启用 1:启用'
);

insert into tb_dept(id, name, create_time, update_time)
values (null, '教务处', now(), now()),
       (null, '学生工作部', now(), now()),
       (null, '教学质量监控中心', now(), now()),
       (null, '科研项目管理处', now(), now()),
       (null, '国际教育交流学院', now(), now()),
       (null, '继续教育学院', now(), now()),
       (null, '招生与就业指导处', now(), now()),
       (null, '教育技术装备中心', now(), now()),
       (null, '教师发展研究院', now(), now()),
       (null, '学籍与学位管理办公室', now(), now());

drop table if exists tb_emp;
create table if not exists tb_emp
(
    id          bigint unsigned primary key auto_increment comment 'ID,主键',
    username    varchar(20)      not null unique comment '用户名',
    password    varchar(32)      not null comment '密码',
    name        varchar(10)      not null comment '姓名',
    gender      tinyint unsigned not null comment '性别, 1:男, 2:女',
    phone       char(11)         not null unique comment '手机号',
    job         tinyint unsigned comment '职位, 1:班主任,2:讲师,3:学工主管,4:教研主管,5:咨询师',
    salary      int unsigned comment '薪资',
    image       varchar(300) comment '头像',
    entry_date  date comment '入职日期',
    create_time datetime comment '创建时间',
    update_time datetime comment '修改时间',
    dept_id     bigint unsigned
) comment '员工表';

-- 水浒传108位英雄好汉数据插入
-- 职位说明: 1:班主任,2:讲师,3:学工主管,4:教研主管,5:咨询师
-- 性别说明: 1:男, 2:女

INSERT INTO tb_emp(username, password, name, gender, phone, job, salary, image, entry_date, create_time, update_time,
                   dept_id)
VALUES
-- 天罡星三十六员
('songjiang', '123456', '宋江', 1, '13309090101', 4, 15000, 'songjiang.jpg', '2010-01-01', NOW(), NOW(), 1),
('lujunyi', '123456', '卢俊义', 1, '13309090102', 2, 12000, 'lujunyi.jpg', '2011-02-01', NOW(), NOW(), 1),
('wuyong', '123456', '吴用', 1, '13309090103', 2, 11000, 'wuyong.jpg', '2010-03-01', NOW(), NOW(), 1),
('gongsunsheng', '123456', '公孙胜', 1, '13309090104', 2, 10500, 'gongsunsheng.jpg', '2011-04-01', NOW(), NOW(), 1),
('guan_sheng', '123456', '关胜', 1, '13309090105', 2, 10000, 'guansheng.jpg', '2012-05-01', NOW(), NOW(), 1),
('lin_chong', '123456', '林冲', 1, '13309090106', 1, 8000, 'linchong.jpg', '2010-06-01', NOW(), NOW(), 1),
('qin_ming', '123456', '秦明', 1, '13309090107', 2, 9500, 'qinming.jpg', '2011-07-01', NOW(), NOW(), 1),
('huyanzhuo', '123456', '呼延灼', 1, '13309090108', 2, 9800, 'huyanzhuo.jpg', '2012-08-01', NOW(), NOW(), 1),
('huarong', '123456', '花荣', 1, '13309090109', 2, 9200, 'huarong.jpg', '2010-09-01', NOW(), NOW(), 1),
('chai_jin', '123456', '柴进', 1, '13309090110', 1, 7500, 'chaijin.jpg', '2011-10-01', NOW(), NOW(), 1),
('li_ying', '123456', '李应', 1, '13309090111', 1, 7800, 'liying.jpg', '2012-11-01', NOW(), NOW(), 1),
('zhu_tong', '123456', '朱仝', 1, '13309090112', 2, 9000, 'zhutong.jpg', '2010-12-01', NOW(), NOW(), 1),
('lu_zhishen', '123456', '鲁智深', 1, '13309090113', 2, 8800, 'luzhishen.jpg', '2011-01-01', NOW(), NOW(), 1),
('wusong', '123456', '武松', 1, '13309090114', 1, 8200, 'wusong.jpg', '2012-02-01', NOW(), NOW(), 1),
('dong_ping', '123456', '董平', 1, '13309090115', 2, 8700, 'dongping.jpg', '2010-03-01', NOW(), NOW(), 1),
('zhang_qing2', '123456', '张清', 1, '13309090116', 2, 8500, 'zhangqing.jpg', '2011-04-01', NOW(), NOW(), 1),
('yang_zhi', '123456', '杨志', 1, '13309090117', 1, 8000, 'yangzhi.jpg', '2012-05-01', NOW(), NOW(), 1),
('xu_ning', '123456', '徐宁', 1, '13309090118', 2, 8300, 'xuning.jpg', '2010-06-01', NOW(), NOW(), 1),
('suo_chao', '123456', '索超', 1, '13309090119', 2, 8100, 'suochao.jpg', '2011-07-01', NOW(), NOW(), 1),
('dai_zong', '123456', '戴宗', 1, '13309090120', 2, 8600, 'daizong.jpg', '2012-08-01', NOW(), NOW(), 1),
('liu_tang', '123456', '刘唐', 1, '13309090121', 1, 7800, 'liutang.jpg', '2010-09-01', NOW(), NOW(), 1),
('li_kui', '123456', '李逵', 1, '13309090122', 1, 7500, 'likui.jpg', '2011-10-01', NOW(), NOW(), 1),
('shi_jin', '123456', '史进', 1, '13309090123', 2, 8900, 'shijin.jpg', '2012-11-01', NOW(), NOW(), 1),
('mu_hong', '123456', '穆弘', 1, '13309090124', 2, 8400, 'muhong.jpg', '2010-12-01', NOW(), NOW(), 1),
('lei_heng', '123456', '雷横', 1, '13309090125', 2, 8200, 'leiheng.jpg', '2011-01-01', NOW(), NOW(), 1),
('li_jun', '123456', '李俊', 1, '13309090126', 5, 7000, 'lijun.jpg', '2012-02-01', NOW(), NOW(), 1),
('ruan_xiaoer', '123456', '阮小二', 1, '13309090127', 5, 6800, 'ruanxiaoer.jpg', '2010-03-01', NOW(), NOW(), 1),
('zhang_heng', '123456', '张横', 1, '13309090128', 5, 6500, 'zhangheng.jpg', '2011-04-01', NOW(), NOW(), 1),
('ruan_xiaowu', '123456', '阮小五', 1, '13309090129', 5, 6700, 'ruanxiaowu.jpg', '2012-05-01', NOW(), NOW(), 1),
('zhang_shun', '123456', '张顺', 1, '13309090130', 5, 6900, 'zhangshun.jpg', '2010-06-01', NOW(), NOW(), 1),
('ruan_xiaoqi', '123456', '阮小七', 1, '13309090131', 5, 6600, 'ruanxiaoqi.jpg', '2011-07-01', NOW(), NOW(), 1),
('yang_xiong', '123456', '杨雄', 1, '13309090132', 2, 8000, 'yangxiong.jpg', '2012-08-01', NOW(), NOW(), 1),
('shi_xiu', '123456', '石秀', 1, '13309090133', 2, 7900, 'shixiu.jpg', '2010-09-01', NOW(), NOW(), 1),
('xie_zhen', '123456', '解珍', 1, '13309090134', 1, 7200, 'xiezhen.jpg', '2011-10-01', NOW(), NOW(), 1),
('xie_bao', '123456', '解宝', 1, '13309090135', 1, 7100, 'xiebao.jpg', '2012-11-01', NOW(), NOW(), 1),
('yan_qing', '123456', '燕青', 1, '13309090136', 2, 8500, 'yanqing.jpg', '2010-12-01', NOW(), NOW(), 1),

-- 地煞星七十二员
('zhu_wu', '123456', '朱武', 1, '13309090137', 2, 7800, 'zhuwu.jpg', '2011-01-01', NOW(), NOW(), 2),
('huang_xin', '123456', '黄信', 1, '13309090138', 2, 7600, 'huangxin.jpg', '2012-02-01', NOW(), NOW(), 2),
('sun_li', '123456', '孙立', 1, '13309090139', 2, 7400, 'sunli.jpg', '2010-03-01', NOW(), NOW(), 2),
('xuan_zan', '123456', '宣赞', 1, '13309090140', 2, 7200, 'xuanzan.jpg', '2011-04-01', NOW(), NOW(), 2),
('hao_siwen', '123456', '郝思文', 1, '13309090141', 2, 7300, 'haosiwen.jpg', '2012-05-01', NOW(), NOW(), 2),
('han_tao', '123456', '韩滔', 1, '13309090142', 2, 7100, 'hantao.jpg', '2010-06-01', NOW(), NOW(), 2),
('peng_qi', '123456', '彭玘', 1, '13309090143', 2, 7000, 'pengqi.jpg', '2011-07-01', NOW(), NOW(), 2),
('shan_tinggui', '123456', '单廷珪', 1, '13309090144', 2, 6900, 'shantinggui.jpg', '2012-08-01', NOW(), NOW(), 2),
('wei_dingguo', '123456', '魏定国', 1, '13309090145', 2, 6800, 'weidingguo.jpg', '2010-09-01', NOW(), NOW(), 2),
('xiao_rang', '123456', '萧让', 1, '13309090146', 2, 7500, 'xiaorang.jpg', '2011-10-01', NOW(), NOW(), 2),
('pei_xuan', '123456', '裴宣', 1, '13309090147', 2, 7200, 'peixuan.jpg', '2012-11-01', NOW(), NOW(), 2),
('ou_peng', '123456', '欧鹏', 1, '13309090148', 2, 7100, 'oupeng.jpg', '2010-12-01', NOW(), NOW(), 2),
('deng_fei', '123456', '邓飞', 1, '13309090149', 2, 7000, 'dengfei.jpg', '2011-01-01', NOW(), NOW(), 2),
('yan_shun', '123456', '燕顺', 1, '13309090150', 5, 6000, 'yanshun.jpg', '2012-02-01', NOW(), NOW(), 2),
('yang_lin', '123456', '杨林', 1, '13309090151', 2, 6900, 'yanglin.jpg', '2010-03-01', NOW(), NOW(), 2),
('ling_zhen', '123456', '凌振', 1, '13309090152', 2, 6800, 'lingzhen.jpg', '2011-04-01', NOW(), NOW(), 2),
('jiang_jing', '123456', '蒋敬', 1, '13309090153', 2, 6700, 'jiangjing.jpg', '2012-05-01', NOW(), NOW(), 2),
('lv_fang', '123456', '吕方', 1, '13309090154', 2, 6600, 'lvfang.jpg', '2010-06-01', NOW(), NOW(), 2),
('guo_sheng', '123456', '郭盛', 1, '13309090155', 2, 6500, 'guosheng.jpg', '2011-07-01', NOW(), NOW(), 2),
('an_daoquan', '123456', '安道全', 1, '13309090156', 2, 7400, 'andaoquan.jpg', '2012-08-01', NOW(), NOW(), 2),
('huangfu_duan', '123456', '皇甫端', 1, '13309090157', 2, 7300, 'huangfuduan.jpg', '2010-09-01', NOW(), NOW(), 2),
('wang_ying', '123456', '王英', 1, '13309090158', 1, 6200, 'wangying.jpg', '2011-10-01', NOW(), NOW(), 2),
('hu_sanniang', '123456', '扈三娘', 2, '13309090159', 3, 7000, 'husanniang.jpg', '2012-11-01', NOW(), NOW(), 2),
('bao_xu', '123456', '鲍旭', 1, '13309090160', 1, 6100, 'baoxu.jpg', '2010-12-01', NOW(), NOW(), 2),
('fan_rui', '123456', '樊瑞', 1, '13309090161', 2, 6400, 'fanrui.jpg', '2011-01-01', NOW(), NOW(), 2),
('kong_ming', '123456', '孔明', 1, '13309090162', 1, 6000, 'kongming.jpg', '2012-02-01', NOW(), NOW(), 2),
('kong_liang', '123456', '孔亮', 1, '13309090163', 1, 5900, 'kongliang.jpg', '2010-03-01', NOW(), NOW(), 2),
('xiang_chong', '123456', '项充', 1, '13309090164', 1, 5800, 'xiangchong.jpg', '2011-04-01', NOW(), NOW(), 2),
('li_gun', '123456', '李衮', 1, '13309090165', 1, 5700, 'ligun.jpg', '2012-05-01', NOW(), NOW(), 2),
('jin_dajian', '123456', '金大坚', 1, '13309090166', 2, 6300, 'jindajian.jpg', '2010-06-01', NOW(), NOW(), 2),
('ma_lin', '123456', '马麟', 1, '13309090167', 2, 6200, 'malin.jpg', '2011-07-01', NOW(), NOW(), 2),
('tong_wei', '123456', '童威', 1, '13309090168', 5, 5500, 'tongwei.jpg', '2012-08-01', NOW(), NOW(), 2),
('tong_meng', '123456', '童猛', 1, '13309090169', 5, 5400, 'tongmeng.jpg', '2010-09-01', NOW(), NOW(), 2),
('meng_kang', '123456', '孟康', 1, '13309090170', 2, 6100, 'mengkang.jpg', '2011-10-01', NOW(), NOW(), 2),
('hou_jian', '123456', '侯健', 1, '13309090171', 2, 6000, 'houjian.jpg', '2012-11-01', NOW(), NOW(), 2),
('chen_da', '123456', '陈达', 1, '13309090172', 1, 5600, 'chenda.jpg', '2010-12-01', NOW(), NOW(), 2),
('yang_chun', '123456', '杨春', 1, '13309090173', 1, 5500, 'yangchun.jpg', '2011-01-01', NOW(), NOW(), 2),
('zheng_tianshou', '123456', '郑天寿', 1, '13309090174', 1, 5400, 'zhengtianshou.jpg', '2012-02-01', NOW(), NOW(), 2),
('tao_zongwang', '123456', '陶宗旺', 1, '13309090175', 2, 5900, 'taozongwang.jpg', '2010-03-01', NOW(), NOW(), 2),
('song_qing', '123456', '宋清', 1, '13309090176', 5, 5200, 'songqing.jpg', '2011-04-01', NOW(), NOW(), 2),
('yue_he', '123456', '乐和', 1, '13309090177', 2, 5800, 'yuehe.jpg', '2012-05-01', NOW(), NOW(), 2),
('gong_wangsun', '123456', '龚旺', 1, '13309090178', 1, 5300, 'gongwangsun.jpg', '2010-06-01', NOW(), NOW(), 2),
('ding_desun', '123456', '丁得孙', 1, '13309090179', 1, 5200, 'dingdesun.jpg', '2011-07-01', NOW(), NOW(), 2),
('mu_chun', '123456', '穆春', 1, '13309090180', 1, 5100, 'muchun.jpg', '2012-08-01', NOW(), NOW(), 2),
('cao_zheng', '123456', '曹正', 1, '13309090181', 1, 5000, 'caozheng.jpg', '2010-09-01', NOW(), NOW(), 2),
('song_wan', '123456', '宋万', 1, '13309090182', 1, 4900, 'songwan.jpg', '2011-10-01', NOW(), NOW(), 2),
('du_qian', '123456', '杜迁', 1, '13309090183', 1, 4800, 'duqian.jpg', '2012-11-01', NOW(), NOW(), 2),
('xue_yong', '123456', '薛永', 1, '13309090184', 1, 4700, 'xueyong.jpg', '2010-12-01', NOW(), NOW(), 2),
('shi_en', '123456', '施恩', 1, '13309090185', 1, 4600, 'shien.jpg', '2011-01-01', NOW(), NOW(), 2),
('li_zhong', '123456', '李忠', 1, '13309090186', 5, 5100, 'lizhong.jpg', '2012-02-01', NOW(), NOW(), 2),
('zhou_tong', '123456', '周通', 1, '13309090187', 1, 4500, 'zhoutong.jpg', '2010-03-01', NOW(), NOW(), 2),
('tang_long', '123456', '汤隆', 1, '13309090188', 2, 5700, 'tanglong.jpg', '2011-04-01', NOW(), NOW(), 2),
('du_xing', '123456', '杜兴', 1, '13309090189', 1, 4400, 'duxing.jpg', '2012-05-01', NOW(), NOW(), 2),
('zou_yuan', '123456', '邹渊', 1, '13309090190', 1, 4300, 'zouyuan.jpg', '2010-06-01', NOW(), NOW(), 2),
('zou_run', '123456', '邹润', 1, '13309090191', 1, 4200, 'zourun.jpg', '2011-07-01', NOW(), NOW(), 2),
('zhu_gui', '123456', '朱贵', 1, '13309090192', 1, 4100, 'zhugui.jpg', '2012-08-01', NOW(), NOW(), 2),
('zhu_fu', '123456', '朱富', 1, '13309090193', 1, 4000, 'zhufu.jpg', '2010-09-01', NOW(), NOW(), 2),
('cai_fu', '123456', '蔡福', 1, '13309090194', 1, 3900, 'caifu.jpg', '2011-10-01', NOW(), NOW(), 2),
('cai_qing', '123456', '蔡庆', 1, '13309090195', 1, 3800, 'caiqing.jpg', '2012-11-01', NOW(), NOW(), 2),
('li_li', '123456', '李立', 1, '13309090196', 1, 3700, 'lili.jpg', '2010-12-01', NOW(), NOW(), 2),
('li_ji', '123456', '李云', 1, '13309090197', 2, 5600, 'liyun.jpg', '2011-01-01', NOW(), NOW(), 2),
('jiao_ting', '123456', '焦挺', 1, '13309090198', 1, 3600, 'jiaoting.jpg', '2012-02-01', NOW(), NOW(), 2),
('shi_li', '123456', '石勇', 1, '13309090199', 1, 3500, 'shili.jpg', '2010-03-01', NOW(), NOW(), 2),
('sun_xin', '123456', '孙新', 1, '13309090200', 1, 3400, 'sunxin.jpg', '2011-04-01', NOW(), NOW(), 2),
('gu_dasao', '123456', '顾大嫂', 2, '13309090201', 2, 6500, 'gudasao.jpg', '2012-05-01', NOW(), NOW(), 2),
('zhang_qing', '123456', '张青', 1, '13309090202', 1, 3300, 'zhangqing2.jpg', '2010-06-01', NOW(), NOW(), 2),
('sun_erniang', '123456', '孙二娘', 2, '13309090203', 2, 6300, 'sunerniang.jpg', '2011-07-01', NOW(), NOW(), 2),
('wang_dingliu', '123456', '王定六', 1, '13309090204', 1, 3200, 'wangdingliu.jpg', '2012-08-01', NOW(), NOW(), 2),
('yu_baosi', '123456', '郁保四', 1, '13309090205', 1, 3100, 'yubaosi.jpg', '2010-09-01', NOW(), NOW(), 2),
('bai_sheng', '123456', '白胜', 1, '13309090206', 1, 3000, 'baisheng.jpg', '2011-10-01', NOW(), NOW(), 2),
('shi_qian', '123456', '时迁', 1, '13309090207', 2, 5500, 'shiqian.jpg', '2012-11-01', NOW(), NOW(), 2),
('duan_jingzhu', '123456', '段景住', 1, '13309090208', 1, 2900, 'duanjingzhu.jpg', '2010-12-01', NOW(), NOW(), 2);

drop table if exists tb_clazz;

create table tb_clazz
(
    id          int unsigned primary key auto_increment comment 'ID,主键',
    name        varchar(255)     not null unique comment '班级名称',
    room        varchar(255) comment '班级教室',
    begin_date  date             not null comment '开课时间',
    end_date    date             not null comment '结课时间',
    master_id   int unsigned     null comment '班主任ID, 关联员工表ID',
    subject     tinyint unsigned not null comment '学科, 1:java, 2:前端, 3:大数据, 4:Python, 5:Go, 6: 嵌入式',
    create_time datetime comment '创建时间',
    update_time datetime comment '修改时间',
    status      varchar(5)       not null default '未开班'
) comment '班级表';

select count(1) total
from tb_clazz c
         left join tb_emp e on c.master_id = e.id;

insert into tb_clazz
values (null, 'JavaEE100期', '304', '2022-10-15', '2023-02-15', 1, 1, now(), now(), 1),
       (null, 'JavaEE101期', '305', '2021-10-15', '2022-02-15', 2, 2, now(), now(), 1),
       (null, 'Python102期', '306', '2020-10-15', '2021-02-15', 3, 2, now(), now(), 1),
       (null, 'JavaEE103期', '307', '2019-10-15', '2020-02-15', 4, 3, now(), now(), 1),
       (null, '大数据104期', '308', '2018-10-15', '2019-02-15', 5, 3, now(), now(), 1),
       (null, 'JavaEE105期', '309', '2017-10-15', '2018-02-15', 6, 4, now(), now(), 1),
       (null, 'JavaEE106期', '310', '2017-10-15', '2018-02-15', 7, 4, now(), now(), 1),
       (null, 'C++107期', '311', '2021-10-15', '2022-02-15', 8, 5, now(), now(), 1),
       (null, 'JavaEE108期', '312', '2020-10-15', '2021-02-15', 9, 5, now(), now(), 1),
       (null, 'Python109期', '313', '2020-10-15', '2021-02-15', 10, 6, now(), now(), 1),
       (null, 'JavaEE110期', '314', '2021-10-15', '2022-02-15', 11, 6, now(), now(), 1),
       (null, '人工智能120期', '316', '2023-10-15', '2024-02-15', 12, 7, now(), now(), 1),
       (null, 'JavaEE130期', '320', '2024-10-15', '2025-02-15', 13, 7, now(), now(), 1)
;

-- 插入 50 条班级数据
INSERT INTO tb_clazz (name, room, begin_date, end_date, master_id, subject, create_time, update_time, status)
VALUES ('JavaEE111期', '401', '2023-10-15', '2024-02-15', 14, 1, NOW(), NOW(), '未开班'),
       ('JavaEE112期', '402', '2023-11-01', '2024-03-01', 15, 1, NOW(), NOW(), '未开班'),
       ('前端开发113期', '403', '2023-11-10', '2024-03-10', 16, 2, NOW(), NOW(), '未开班'),
       ('Python数据分析114期', '404', '2023-12-01', '2024-04-01', 17, 4, NOW(), NOW(), '未开班'),
       ('大数据处理115期', '405', '2024-01-10', '2024-05-10', 18, 3, NOW(), NOW(), '未开班'),
       ('JavaEE116期', '406', '2024-02-01', '2024-06-01', 19, 1, NOW(), NOW(), '未开班'),
       ('前端开发117期', '407', '2024-02-15', '2024-06-15', 20, 2, NOW(), NOW(), '未开班'),
       ('人工智能118期', '408', '2024-03-01', '2024-07-01', 21, 7, NOW(), NOW(), '未开班'),
       ('嵌入式系统119期', '409', '2024-03-15', '2024-07-15', 22, 6, NOW(), NOW(), '未开班'),
       ('Go语言后端120期', '410', '2024-04-01', '2024-08-01', 23, 5, NOW(), NOW(), '未开班'),
       ('JavaEE121期', '411', '2024-04-15', '2024-08-15', 24, 1, NOW(), NOW(), '未开班'),
       ('前端开发122期', '412', '2024-05-01', '2024-09-01', 25, 2, NOW(), NOW(), '未开班'),
       ('Python自动化测试123期', '413', '2024-05-15', '2024-09-15', 26, 4, NOW(), NOW(), '未开班'),
       ('大数据分析124期', '414', '2024-06-01', '2024-10-01', 27, 3, NOW(), NOW(), '未开班'),
       ('JavaEE125期', '415', '2024-06-15', '2024-10-15', 28, 1, NOW(), NOW(), '未开班'),
       ('前端开发126期', '416', '2024-07-01', '2024-11-01', 29, 2, NOW(), NOW(), '未开班'),
       ('人工智能127期', '417', '2024-07-15', '2024-11-15', 30, 7, NOW(), NOW(), '未开班'),
       ('嵌入式系统128期', '418', '2024-08-01', '2024-12-01', 31, 6, NOW(), NOW(), '未开班'),
       ('Go语言后端129期', '419', '2024-08-15', '2024-12-15', 32, 5, NOW(), NOW(), '未开班'),
       ('JavaEE131期', '420', '2024-09-01', '2025-01-01', 33, 1, NOW(), NOW(), '未开班'),
       ('前端开发132期', '421', '2024-09-15', '2025-01-15', 34, 2, NOW(), NOW(), '未开班'),
       ('Python爬虫133期', '422', '2024-10-01', '2025-02-01', 35, 4, NOW(), NOW(), '未开班'),
       ('大数据处理134期', '423', '2024-10-15', '2025-02-15', 36, 3, NOW(), NOW(), '未开班'),
       ('JavaEE135期', '424', '2024-11-01', '2025-03-01', 37, 1, NOW(), NOW(), '未开班'),
       ('前端开发136期', '425', '2024-11-15', '2025-03-15', 38, 2, NOW(), NOW(), '未开班'),
       ('人工智能137期', '426', '2024-12-01', '2025-04-01', 39, 7, NOW(), NOW(), '未开班'),
       ('嵌入式系统138期', '427', '2024-12-15', '2025-04-15', 40, 6, NOW(), NOW(), '未开班'),
       ('Go语言后端139期', '428', '2025-01-01', '2025-05-01', 41, 5, NOW(), NOW(), '未开班'),
       ('JavaEE140期', '429', '2025-01-15', '2025-05-15', 42, 1, NOW(), NOW(), '未开班'),
       ('前端开发141期', '430', '2025-02-01', '2025-06-01', 43, 2, NOW(), NOW(), '未开班'),
       ('Python机器学习142期', '431', '2025-02-15', '2025-06-15', 44, 4, NOW(), NOW(), '未开班'),
       ('大数据分析143期', '432', '2025-03-01', '2025-07-01', 45, 3, NOW(), NOW(), '未开班'),
       ('JavaEE144期', '433', '2025-03-15', '2025-07-15', 46, 1, NOW(), NOW(), '未开班'),
       ('前端开发145期', '434', '2025-04-01', '2025-08-01', 47, 2, NOW(), NOW(), '未开班'),
       ('人工智能146期', '435', '2025-04-15', '2025-08-15', 48, 7, NOW(), NOW(), '未开班'),
       ('嵌入式系统147期', '436', '2025-05-01', '2025-09-01', 49, 6, NOW(), NOW(), '未开班'),
       ('Go语言后端148期', '437', '2025-05-15', '2025-09-15', 50, 5, NOW(), NOW(), '未开班'),
       ('JavaEE149期', '438', '2025-06-01', '2025-10-01', 51, 1, NOW(), NOW(), '未开班'),
       ('前端开发150期', '439', '2025-06-15', '2025-10-15', 52, 2, NOW(), NOW(), '未开班'),
       ('PythonWeb开发151期', '440', '2025-07-01', '2025-11-01', 53, 4, NOW(), NOW(), '未开班'),
       ('大数据处理152期', '441', '2025-07-15', '2025-11-15', 54, 3, NOW(), NOW(), '未开班'),
       ('JavaEE153期', '442', '2025-08-01', '2025-12-01', 55, 1, NOW(), NOW(), '未开班'),
       ('前端开发154期', '443', '2025-08-15', '2025-12-15', 56, 2, NOW(), NOW(), '未开班'),
       ('人工智能155期', '444', '2025-09-01', '2026-01-01', 57, 7, NOW(), NOW(), '未开班'),
       ('嵌入式系统156期', '445', '2025-09-15', '2026-01-15', 58, 6, NOW(), NOW(), '未开班'),
       ('Go语言后端157期', '446', '2025-10-01', '2026-02-01', 59, 5, NOW(), NOW(), '未开班'),
       ('JavaEE158期', '447', '2025-10-15', '2026-02-15', 60, 1, NOW(), NOW(), '未开班'),
       ('前端开发159期', '448', '2025-11-01', '2026-03-01', 61, 2, NOW(), NOW(), '未开班'),
       ('Python数据分析160期', '449', '2025-11-15', '2026-03-15', 62, 4, NOW(), NOW(), '未开班');


-- 工作经历表

drop table if exists tb_empexpr;

create table if not exists tb_empexpr
(
    `id`      bigint unsigned primary key auto_increment,
    `begin`   date,
    `end`     date,
    `company` varchar(50),
    `job`     varchar(50),
    `tb_emp_id` bigint
);

-- 添加50个学生

-- 员工日志表
drop table if exists tb_log_emp;
create table if not exists tb_log_emp
(
    `id`        int unsigned primary key auto_increment,
    `pre_data`  text             not null,
    `post_data` text             not null,
    `opt_time`  datetime         not null,
    `opt_type`  tinyint unsigned not null comment '1: insert, 2:delete, 3:update',
    `opt_res`   tinyint unsigned not null comment '1: success 2: failed 3: error'
);

-- 查询隔离级别
select @@transaction_isolation;


update tb_emp
set image = 'http://127.0.0.1:5500/8/2/11/0/10/15/4/0/e63913d7-3fd4-477c-bacc-c5da56de81c5_cat32.jpg'
where id > 0;


select e.*,
       d.name      as 'dname',
       r.id        as rid,
       r.begin     as rbegin,
       r.end       as rend,
       r.job       as rjob,
       r.company   as rcompany,
       r.tb_emp_id as rteid
from tb_emp as e
         left join tb_dept as d on e.dept_id = d.id
         left join tb_empexpr as r on e.id = r.tb_emp_id;

select *
from tb_empexpr
WHERE tb_emp_id IN (1, 2, 3);

select er.tb_emp_id, er.*
from tb_empexpr er
order by er.tb_emp_id;


select e.*, d.name dname r.id rid, r.begin rbegin, r.end rend, r.job rjob, r.tb_emp_id rteid, r.company rcompany
from tb_emp e
         left join tb_empexpr r on e.id = r.tb_emp_id
         left join tb_dept d on e.dept_id = d.id
WHERE e.id IN (?, ?, ?)


-- 操作日志表
create table tb_opt_log
(
    id             int unsigned primary key auto_increment comment 'ID',
    operate_emp_id int unsigned comment '操作人ID',
    operate_time   datetime comment '操作时间',
    class_name     varchar(100) comment '操作的类名',
    method_name    varchar(100) comment '操作的方法名',
    method_params  varchar(1000) comment '方法参数',
    return_value   varchar(2000) comment '返回值, 存储json格式',
    cost_time      int comment '方法执行耗时, 单位:ms'
) comment '操作日志表';

update tb_clazz
set status='已开班'
where begin_date <= now()
  and end_date >= now();

update tb_clazz
set status='已结束'
where end_date <= now();



SELECT CASE e.job
           WHEN 1 THEN '班主任'
           WHEN 2 THEN '讲师'
           WHEN 3 THEN '学工主管'
           WHEN 4 THEN '教研主管'
           WHEN 5 THEN '咨询师' END AS 'jobName',
       COUNT(job)                   AS 'jobCount'
FROM tb_emp e
GROUP BY e.job;


SELECT CASE e.job
           WHEN 1 THEN '班主任'
           WHEN 2 THEN '讲师'
           WHEN 3 THEN '学工主管'
           WHEN 4 THEN '教研主管'
           WHEN 5 THEN '咨询师' END AS 'jobName',
       job
FROM tb_emp e
group by e.job;


-- # select count(er.tb_emp_id) 'exps' -- d.name
select er.tb_emp_id, count(er.tb_emp_id)
from tb_empexpr er
--          left join tb_emp e on e.id = er.tb_emp_id
--         left join tb_dept d on d.id = e.dept_id
group by er.tb_emp_id;


select e.*,
       d.name as 'dname',
from tb_emp as e
         left join tb_dept as d on e.dept_id = d.id;