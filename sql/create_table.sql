-- 创建库
create database if not exists search_backend;

-- 切换库
use search_backend;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    user_account  varchar(256)                           not null comment '账号',
    user_password varchar(512)                           not null comment '密码',
    union_id      varchar(256)                           null comment '微信开放平台id',
    mp_open_id     varchar(256)                           null comment '公众号openId',
    user_name     varchar(256)                           null comment '用户昵称',
    user_avatar   varchar(1024)                          null comment '用户头像',
    user_profile  varchar(512)                           null comment '用户简介',
    user_role     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    create_time   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete     tinyint      default 0                 not null comment '是否删除',
    index idx_union_id (union_id)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumb_num   int      default 0                 not null comment '点赞数',
    favour_num  int      default 0                 not null comment '收藏数',
    user_id     bigint                             not null comment '创建用户 id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint  default 0                 not null comment '是否删除',
    index idx_user_id (user_id)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    post_id     bigint                             not null comment '帖子 id',
    user_id     bigint                             not null comment '创建用户 id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_post_id (post_id),
    index idx_user_id (user_id)
) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    post_id     bigint                             not null comment '帖子 id',
    user_id     bigint                             not null comment '创建用户 id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_post_id (post_id),
    index idx_user_id (user_id)
) comment '帖子收藏';

-- 模拟数据
INSERT  INTO `post`(`id`,`title`,`content`,`tags`,`thumb_num`,`favour_num`,`user_id`,`create_time`,`update_time`,`is_delete`) VALUES (1633791753154785282,'铮铮誓言','为让学生走出大山 “哪怕剩下最后一口气我都会用在山区孩子的教育上”为守好祖国每寸领土“宁可前进一步死绝不后退半步生”为科技兴国“祖国需要我，我义无反顾”一句句誓言是他们用一生践行的初心和使命！','[\"xinlang\",\"xinwen\"]',0,0,1633392756661444610,'2023-03-09 11:28:02','2023-03-09 11:28:02',0);
INSERT  INTO `post`(`id`,`title`,`content`,`tags`,`thumb_num`,`favour_num`,`user_id`,`create_time`,`update_time`,`is_delete`) VALUES (1633791965642420226,'金融监管改革','一是关于组建国家金融监督管理总局。地方金融监管的改革方案中，地方政府设立的金融监管机构专司监管职责，有利于加强监管，提高效率，保护消费者权益。二是社会主义市场经济的本质特征就是坚持党的领导，要为化解风险提供有力保障。三是完善金融资本管理体制，金融管理部门管理的金融基础设施，在改革方案落实中要稳妥。四是加强金融机构工作人员的统一规范管理，要做好切实可行的方案。','[\"xinlang\",\"xinwen\"]',0,0,1633392756661444610,'2023-03-09 11:28:53','2023-03-09 11:28:53',0);
INSERT  INTO `post`(`id`,`title`,`content`,`tags`,`thumb_num`,`favour_num`,`user_id`,`create_time`,`update_time`,`is_delete`) VALUES (1633792204495450113,'泽连斯基亲邀访乌','俄乌军事冲突升级一年多以来，已有数十名美国民主党人和共和党人访问过乌克兰。就在上个月，美国总统拜登秘密到访基辅，承诺向乌提供约5亿美元军事援助。一周后，财长耶伦也突访基辅，宣布追加约12.5亿美元经济援助。上周，司法部长加兰会见泽连斯基，并在利沃夫出席会议。','[\"xinlang\",\"xinwen\",\"wukelan\"]',0,0,1633392756661444610,'2023-03-09 11:29:50','2023-03-09 11:29:50',0);
INSERT  INTO `post`(`id`,`title`,`content`,`tags`,`thumb_num`,`favour_num`,`user_id`,`create_time`,`update_time`,`is_delete`) VALUES (1633792380295507969,'尹锡悦将访日','尹锡悦政府公布日本二战时期强征劳工受害者赔偿问题的解决方案，韩方将设立公共赔偿基金，代替被告日企支付赔偿金和利息，由此引发韩国国内抗议声不断。','[\"xinlang\",\"xinwen\",\"riben\"]',0,0,1633392756661444610,'2023-03-09 11:30:32','2023-03-09 11:30:32',0);
INSERT  INTO `post`(`id`,`title`,`content`,`tags`,`thumb_num`,`favour_num`,`user_id`,`create_time`,`update_time`,`is_delete`) VALUES (1633792568154189826,'被“211”母校基金会起诉，90后男子已成“老赖”','2019年4月，中国矿业大学迎来110周年校庆，90后校友吴幽宣布捐赠1100万元。然而这笔捐赠并没有到位，吴幽反而惹来了官司。据公开消息，2023年1月，吴幽被列为被执行人，被纳入失信名单，收到限制消费令，成为了“老赖”。','[\"xinlang\",\"xinwen\",\"shixin\"]',0,0,1633392756661444610,'2023-03-09 11:31:17','2023-03-09 11:37:37',0);
INSERT  INTO `post`(`id`,`title`,`content`,`tags`,`thumb_num`,`favour_num`,`user_id`,`create_time`,`update_time`,`is_delete`) VALUES (1633793645654110210,'“奋斗者日”“乐捐”都是侵犯劳动者权益的马甲”','一则发生在大公司。3月7日，一封奇瑞汽车高管发布的内部邮件被曝光。邮件中奇瑞高管在回复研发出勤统计的邮件时写道：“以奋斗者为本，周六是奋斗者的正常工作日。对于行政领导们，必须是正常工作日，请想办法（规避法律风险）。” 3月8日，该高管回应称，邮件要求的对象并非普通员工，而是希望激发愿意努力工作的员工，鼓励他们奋斗，同时不让他们吃亏，邮件内容本意并非压榨员工。但对于外界关注的“规避法律风险”这一说辞并未回应。','[\"xinlang\",\"xinwen\",\"quanyi\"]',0,0,1633392756661444610,'2023-03-09 11:35:34','2023-03-09 11:35:34',0);
INSERT  INTO `user` (id, user_account, user_password, union_id, mp_open_id, user_name, user_avatar, user_profile, user_role, create_time, update_time, is_delete) VALUES (1633392756661444610, 'LiuJian', 'caafc33ce9d641f5bc8e367d7aa7d03c', null, null, '鸡哥', null, '厉不厉害你鸡哥', 'admin', '2023-03-14 02:14:05', '2023-03-14 10:54:01', 0);
INSERT  INTO `user` (id, user_account, user_password, union_id, mp_open_id, user_name, user_avatar, user_profile, user_role, create_time, update_time, is_delete) VALUES (1635464278066716675, 'zhangSan', 'caafc33ce9d641f5bc8e367d7aa7d03c', null, null, '张三', null, '厉不厉害你张哥', 'user', '2023-03-14 09:00:20', '2023-03-14 10:54:01', 0);
INSERT  INTO `user` (id, user_account, user_password, union_id, mp_open_id, user_name, user_avatar, user_profile, user_role, create_time, update_time, is_delete) VALUES (1635464278066716676, 'LiSi', 'caafc33ce9d641f5bc8e367d7aa7d03c', null, null, '李四', null, '厉不厉害你李哥', 'user', '2023-03-14 09:00:20', '2023-03-14 10:54:01', 0);

