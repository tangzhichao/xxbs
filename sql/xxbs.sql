/*创建数据库xxbs*/
DROP DATABASE IF EXISTS xxbs;
CREATE DATABASE IF NOT EXISTS xxbs DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

/*切换到xxbs_blog数据库*/
USE xxbs;

/*地区表*/
DROP TABLE IF EXISTS `area`;
CREATE TABLE IF NOT EXISTS `area`(
	id  INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	parent_id INT COMMENT'父级区域的id，顶级此值为null',
	`name`  VARCHAR(32) NOT NULL COMMENT '地区名称',
    `short_name`  VARCHAR(32) NOT NULL COMMENT '地区简称',
    `postfix`  VARCHAR(10) NOT NULL COMMENT '地区后缀，如省、市、自治区、特别行政区',
	`level` INT NOT NULL DEFAULT 0 COMMENT'层级，1为省份，2为市，以此类推',
    `sort_order` varchar(5) NOT NULL COMMENT '显示顺序，越小越靠前',
	UNIQUE KEY(parent_id,`name`)
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'地区表';

/*权限表*/
DROP TABLE IF EXISTS `permission`;
CREATE TABLE IF NOT EXISTS `permission`(
	id  INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	parent_id INT COMMENT'父权限的id，顶级此值为null，外键',
	`name` VARCHAR(20) NOT NULL COMMENT'权限名',
	`key` VARCHAR(20) NOT NULL COMMENT'权限英文关键字',
	`url` VARCHAR(200) COMMENT'权限路径',
	UNIQUE KEY(parent_id,`name`),
	UNIQUE KEY(parent_id,`key`)
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'权限表';

/*角色表*/
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role`(
	id  INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	parent_id INT COMMENT'父角色的id，顶级此值为null，外键',
	`name` VARCHAR(30) NOT NULL COMMENT'角色名称',
    `key` VARCHAR(20) NOT NULL COMMENT'角色英文关键字',
 	UNIQUE KEY(parent_id,`name`),
    UNIQUE KEY(parent_id,`key`)
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'角色表';

/*角色与权限关系表*/
DROP TABLE IF EXISTS `role_permission_relation`;
CREATE TABLE IF NOT EXISTS `role_permission_relation`(
	id  INT PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT'主键',
	role_id INT NOT NULL COMMENT'角色id，外键',
	permission_id INT NOT NULL COMMENT'权限id，外键',
	UNIQUE KEY(`role_id`,permission_id)
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'角色与权限关系表';

/*学习阶段（学历）表*/
DROP TABLE IF EXISTS `learning_phase`;
CREATE TABLE IF NOT EXISTS `learning_phase`(
	id  INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	parent_id INT COMMENT'父级标签的id，顶级此值为null，外键',
	`name` VARCHAR(20) NOT NULL COMMENT'学习阶段名称，如小学、小学一年级等',
    `key` VARCHAR(20) NOT NULL COMMENT'学习阶段英文关键字',
	`level` INT NOT NULL DEFAULT 0 COMMENT'层级，1为顶级标签，2为次级标签，以此类推',
    UNIQUE KEY(parent_id,`name`),
    UNIQUE KEY(parent_id,`key`)
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'学习阶段表';

/*考试（面试）类型表*/
DROP TABLE IF EXISTS `exam`;
CREATE TABLE IF NOT EXISTS `exam`(
	id  INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	`type_name` VARCHAR(20) NOT NULL COMMENT'考试类别名称，如英语四级',
	`name` VARCHAR(20) NOT NULL COMMENT'考试名称，如湖南英语四级（1）',
	area_id int comment'考试地区，外键',
	learning_phase_id int not null comment '答题应该需要的学历（学习阶段），外键',
    UNIQUE KEY(type_name,`name`,area_id,learning_phase_id)
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'考试（面试）类型表';

/*用户表*/
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user`(
	id int primary key auto_increment not null,
	phone_number VARCHAR(50) unique not null comment '手机号',
    email VARCHAR(50) comment '如果用户绑定邮箱，则允许使用邮箱登录',
    nick_name VARCHAR(20) comment '用户可以设置一个昵称用来对他人展示',
    `password` VARCHAR(50) comment'录入数据前校验最短6个字符，最长12个字符，然后md5加密再保存到数据库',
    
    role_id int not null comment'用户角色id，外键',
    
    sex int default 0 comment '性别，0男，1女，其他数字为“其他”',
    brithday datetime comment '出生日期（年月日）',
	brithday_calendar int default 0 comment '出生日期的日历，0公历，1农历',
    age int comment '年龄，录入数据前校验不能为负数，如果填了生日自动生成年龄',
    images VARCHAR(150) comment'用户头像，多张用英文分号“;”分隔，最多3张，默认显示第一张',
    emotional_state int comment '情感状态，0单身，1热恋，2已婚，3离异',
   	statement VARCHAR(50) comment'个人说明',
   	learning_phase_id int not null default 0 comment '答题应该需要的学历（学习阶段），外键',
   	
   	`level` int not null default 1 comment'用户等级',
    gold decimal(10,2) not null default 0.0 comment'用户虚拟金币',
   	
   	lat decimal(20,17) comment'用户当前所在纬度',
  	lng decimal(20,17) comment'用户当前所在经度',
  	city varchar(100) comment'用户当前所在城市',
   	live_area_id int comment'用户生活和居住地区id（用户所选），外键',
   	current_area_id int comment'用户当前所在地区id，此字段不应该由用户填写而应该由程序自动更新，外键',
   
    learn_mode_is_open int not null default 0 comment '是否开启学习模式,0不开启，1开启',
    ready_exam_id int comment '正在准备的考试，外键',
    answer_count int not null default 0 comment '答题个数',
    answer_correct_count int not null default 0 comment '答题正确个数',
    match_count int not null default 0 comment '比赛次数',
    match_win_count int not null default 0 comment '比赛胜利次数',
    
	last_login_time datetime comment'最后登录时间',
	last_login_ip VARCHAR(50) comment'最后登录ip地址',
	last_modify_time datetime comment'该数据最后修改时间',
    recorded_time datetime not null comment'录入数据时的时间',
    valid int not null default 1 comment '该用户是否有效，0系统封号，1正常，2防盗号锁定，4删除'
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'用户表';

/*用户与用户的关系表（由于好友是双向的，所以添加好友时应该在source_user_id和target_user_id互换的方式插入两条数据）*/
DROP TABLE IF EXISTS `user_friend_relation`;
CREATE TABLE IF NOT EXISTS `user_friend_relation`(
	id  INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	source_user_id int not null comment '发起好友申请的源用户id，外键',
	target_user_id int not null comment '被源用户申请加为好友的用户id，外键',
  	last_modify_time datetime comment'该数据最后修改时间',
    recorded_time datetime not null comment'录入数据时的时间',
	`status` int NOT NULL default 1 COMMENT'关系状态，1正常好友，2已删除好友,3已关注，4已取消关注，5已拉黑，6已取消拉黑',
    unique key(source_user_id,target_user_id)
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'用户与用户的好友关系表';

/*文章表（朋友圈、发帖）*/
DROP TABLE IF EXISTS `blog`;
CREATE TABLE IF NOT EXISTS `blog`(
	id int primary key auto_increment not null,
    user_id int not null comment '用户id，外键',
    blog_type int not null default 1 comment '文章类型，1朋友圈，2发帖，3新闻（系统发布），4系统公告（系统发布）',
	open_type int not null default 1 comment '对外公开类型（仅在文章类型为朋友圈时生效），0保密（仅自己可见），1对所有人公开，2对好友公开，3对关注自己的人公开',
    source_blog_id int comment'如果该文章为转发，则此字段为源文章id，外键',
	`title` VARCHAR(500) not null comment'文章标题，如果用户没写标题，则系统自动截取content字段作为标题',
	
	attachment VARCHAR(1000) comment'附件（只允许图片、视频、音频格式），多个用英文分号“;”分隔，最多6个，默认显示第一个',
    attachment_type int comment '附件类型，0图片，1视频，2音频，多个用英文分号“;”分隔，最多6个',
    
    city VARCHAR(200) comment '发布文章时所定位到（或用户所选）的用户所在地区名称',
    
    content text comment'文章内容（描叙），允许保存html格式内容，如果文章类型是朋友圈，则无此字段',
    is_anonymous int not null default 1 comment '是否匿名，0匿名，1不匿名，如果文章类型是朋友圈，则无此字段',
    comment_setting int not null default 1 comment '评论设置，0关闭评论，1任何人都可评论，2只允许我关注的人评论，3由我筛选后显示，如果文章类型是朋友圈，则无此字段',
    
	praise_count int not null default 0 comment '点赞个数，此字段为简化系统查询',
    trample_count int not null default 0 comment '点踩个数，此字段为简化系统查询',
    comment_count int not null default 0 comment '评论个数，此字段为简化系统查询',
    
    publish_time datetime comment'指定发布日期',
    
    last_modify_time datetime comment'该数据最后修改时间',
    recorded_time datetime not null comment'录入数据时的时间',
	`status` int not null default 0 comment '文章状态，0待审核，1审核通过，2审核不通过，3系统封禁（仅用户自己可见），4系统删除，5用户删除'
)CHARSET=utf8 COLLATE=utf8_general_ci comment'文章表';

/*文章标签（话题、分类）表*/
DROP TABLE IF EXISTS `tag`;
CREATE TABLE IF NOT EXISTS `tag`(
	id  INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	parent_id INT COMMENT'父级标签的id，顶级此值为null，外键',
	`name` VARCHAR(20) NOT NULL COMMENT'标签名称',
	`level` INT NOT NULL DEFAULT 1 COMMENT'层级，1为顶级标签，2为次级标签，以此类推',
	`type` INT NOT NULL DEFAULT 2 COMMENT'类型，1为小学中学大学的科目，2为生活类，3系统新闻，4系统公告，5考点知识点',
    recorded_time datetime not null comment'录入数据时的时间',
    unique key(parent_id,`name`,`type`)
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'（文章）标签表';

/*文章标签关系表*/
DROP TABLE IF EXISTS `blog_tag_relation`;
CREATE TABLE IF NOT EXISTS `blog_tag_relation`(
	id int primary key auto_increment not null,
	blog_id int not null comment '文章id，外键',
    tag_id int not null comment '标签id，外键',
    recorded_time datetime not null comment'录入数据时的时间',
    unique key(blog_id,tag_id)
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'文章标签关系表';

/*用户关注标签表*/
DROP TABLE IF EXISTS `user_tag_relation`;
CREATE TABLE IF NOT EXISTS `user_tag_relation`(
	id int primary key auto_increment not null,
	user_id int not null comment '用户id，外键',
    tag_id int not null comment '标签id，外键',
    last_modify_time datetime comment'该数据最后修改时间',
    recorded_time datetime not null comment'录入数据时的时间',
	`status` int not null default 1 comment '关注状态，0已取消关注，1已关注',
	unique key(user_id,tag_id)
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'文章标签关系表';

/*评论表*/
DROP TABLE IF EXISTS `comment`;
CREATE TABLE IF NOT EXISTS `comment`(
	id int primary key auto_increment not null,
    user_id int not null comment '用户id，外键',
    target_blog_id int  comment '目标文章id，如果此字段不为空，则表示该评论为一级评论，外键',
    target_comment_id int  comment '目标评论id，如果此字段不为空，则表示该评论为非一级评论，外键',
    content text NOT NULL comment'内容',
    floor_number int comment '楼层数，一级评论才有此字段',
	praise_count int not null default 0 comment '点赞个数，此字段为简化系统查询',
    trample_count int not null default 0 comment '点踩个数，此字段为简化系统查询',
    attachment VARCHAR(1000) comment'附件（只允许图片、视频、音频格式），多个用英文分号“;”分隔，最多6个，默认显示第一个',
    attachment_type int comment '附件类型，0图片，1视频，2音频，多个用英文分号“;”分隔，最多6个',
    open_type int not null default 1 comment '对外公开类型，0只对“目标文章主人”或“目标评论主人”可见，1对所有人可见',
    last_modify_time datetime comment'该数据最后修改时间',
    recorded_time datetime not null comment'录入数据时的时间',
	`status` int not null default 1 comment '状态，0待目标文章主人审核，1审核通过，2审核不通过，3系统删除，4文章主人删除，5用户自己删除'
)CHARSET=utf8 COLLATE=utf8_general_ci comment'评论表';

/*点赞点踩表*/
DROP TABLE IF EXISTS `praise_trample`;
CREATE TABLE IF NOT EXISTS `praise_trample`(
	id int primary key auto_increment not null,
    user_id int not null comment '用户id，外键',
    blog_id int comment '文章id，如果此字段不为空，则表示该点赞为文章点赞，外键',
    comment_id int comment '评论id，如果此字段不为空，则表示该点赞为评论点赞，外键',
    last_modify_time datetime comment'该数据最后修改时间',
    recorded_time datetime not null comment'录入数据时的时间',
	`status` int not null comment '点赞点踩状态，1已点赞，2已取消点赞，3已点踩，4已取消点踩',
	unique key(user_id,blog_id),
	unique key(user_id,comment_id)
)CHARSET=utf8 COLLATE=utf8_general_ci comment'点赞点踩表';

/*团队表*/
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
	id int primary key auto_increment not null,
    create_user_id int not null comment '创建者用户id，外键',
    leader_user_id int not null comment '群主用户id，此字段为简化系统查询，外键',
	`name` VARCHAR(30) unique NOT NULL COMMENT '群名称',
    image VARCHAR(50) COMMENT '群图标',
    `type` int not null default 1 comment '团队类型，1普通团队，2师徒，3婚姻',
	city VARCHAR(200) NOT NULL COMMENT '群创建时所在地区或所选地区名称',
	description varchar(100) comment '群简介',
	last_modify_time datetime comment'该数据最后修改时间',
    recorded_time datetime not null comment'录入数据时的时间',
	`status` int NOT NULL DEFAULT 0 COMMENT '0待审核，1审核通过，2审核不通过，3群主解散，4系统删除'
)CHARSET=utf8 COLLATE=utf8_general_ci comment'团队表';

/*群与群成员用户关系表*/
DROP TABLE IF EXISTS `team_user_relation`;
CREATE TABLE `team_user_relation` (
	id int primary key auto_increment not null,
    team_id int not null comment '群id，外键',
    user_id int not null comment '用户id，外键',
    team_user_name varchar(30) comment '用户在群里的名称',
    team_user_role varchar(30) not null comment '用户在群里的角色',
    `type` int not null default 0 comment '关系类型，0群主，1管理员，2普通成员',
	last_modify_time datetime comment'该数据最后修改时间',
    recorded_time datetime not null comment'录入数据时的时间',
    unique key(team_id,user_id)
)CHARSET=utf8 COLLATE=utf8_general_ci comment'群与群成员用户关系表';

/*消息通知表*/
DROP TABLE IF EXISTS `notification`;
CREATE TABLE IF NOT EXISTS `system_message`(
	id int primary key auto_increment not null,
    `title` VARCHAR(20) comment'消息标题',
    content VARCHAR(1000) NOT NULL comment'内容',
    datainfo VARCHAR(500) comment '其他需要用到的数据、数据id',
    `type` int NOT NULL DEFAULT 0 COMMENT '消息类型，0私信，1系统公告，2好友申请，3群申请，4有评论需我审核,5有人点赞，6有人评论,7有人关注我，8有人@我（与我相关）',
    target_user_id int not null comment '接受此消息的目标用户id，外键',
    source_user_id int not null comment '发起此消息的源用户id，外键',
    source_blog_id int comment '引发此通知的文章id，外键',
    source_comment_id int comment '引发此通知的评论id，外键',
	last_modify_time datetime comment'该数据最后修改时间',
    recorded_time datetime not null comment'录入数据时的时间',
    `status` int not null comment '消息状态，0未读，1已读'
)CHARSET=utf8 COLLATE=utf8_general_ci comment'消息通知表';

/*用户反馈表*/
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
	id int primary key auto_increment not null,
	user_id int not null comment '用户id，外键',
	`type` int NOT NULL DEFAULT 0 COMMENT '类型，0普通bug反馈',
	content varchar(300) NOT NULL comment '反馈内容',
	attachment VARCHAR(1000) comment'附件（只允许图片、视频、音频格式），多个用英文分号“;”分隔，最多6个',
    attachment_type int comment '附件类型，0图片，1视频，2音频，多个用英文分号“;”分隔，最多6个',
	`status` int NOT NULL DEFAULT 0 comment '状态，0未处理，1处理中，2已处理完成，3不予处理',
	no_handle_reason varchar(300) comment '不予处理理由',
	last_modify_time datetime comment'该数据最后修改时间',
    recorded_time datetime not null comment'录入数据时的时间'
)CHARSET=utf8 COLLATE=utf8_general_ci comment'用户反馈表';

/*用户举报表*/
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
	id int primary key auto_increment not null,
	source_user_id int not null comment '用户id，外键',
	target_user_id int not null comment '被举报用户id，外键',
	target_blog_id int comment '被举报的文章id，外键',
    target_comment_id int comment '被举报的评论id，外键',
	`type` int NOT NULL DEFAULT 5 COMMENT '类型，0侵权，1垃圾，2黄赌毒，3政治敏感，4不规范转载，5辱骂歧视，6广告',
	content varchar(300) NOT NULL comment '反馈内容',
	attachment VARCHAR(1000) comment'附件（只允许图片、视频、音频格式），多个用英文分号“;”分隔，最多6个',
    attachment_type int comment '附件类型，0图片，1视频，2音频，多个用英文分号“;”分隔，最多6个',
	`status` int NOT NULL DEFAULT 0 comment '状态，0未处理，1处理中，2已处理完成，3不予处理',
	no_handle_reason varchar(300) comment '不予处理理由',
	last_modify_time datetime comment'该数据最后修改时间',
    recorded_time datetime not null comment'录入数据时的时间'
)CHARSET=utf8 COLLATE=utf8_general_ci comment'用户举报表';


/*问题表*/
DROP TABLE IF EXISTS `question`;
CREATE TABLE IF NOT EXISTS `question`(
	id int primary key auto_increment not null,
    user_id int not null comment '创建者id，外键',
	`title` VARCHAR(100) not null comment'标题，（此列不能设唯一）',
	remarks VARCHAR(300) not null comment'问题备注（不予展示，只给审核人员看）',
	analysis VARCHAR(500) comment'问题解析',
    create_from int not null default 0 comment '问题来源，0系统管理员创建，1普通用户创建',
    `type` int not null default 0 comment '问题类型，0单选题，1多选题，2填空题，如果是填空题，则只需要在选项表里添加一个选项即可',
    answer_count int not null default 0 comment '用户回答总次数，此字段为简化系统查询',
    points decimal(10,2) not null default 0 comment '问题分数',
    answer_second int not null default 60 comment '答题应该需要的时间（秒）',
    learning_phase_id int not null comment '答题应该需要的学历（学习阶段），外键',
    knowledge_point_tag_id int comment '知识点，外键',
    end_time datetime comment'结束日期（年月日），如果该字段为空表示不限',
    last_modify_time datetime comment'该数据最后修改时间',
    recorded_time datetime not null comment'录入数据时的时间',
    `status` int not null comment '状态，0待审核，1审核通过，2审核不通过，3系统删除'
)CHARSET=utf8 COLLATE=utf8_general_ci comment'问题问题表';

/*问题选项表*/
DROP TABLE IF EXISTS `option`;
CREATE TABLE IF NOT EXISTS `option`(
	id int primary key auto_increment not null,
	question_id int not null comment '问题id，外键',
    sort_order varchar(5) NOT NULL COMMENT '选项显示顺序，越小越靠前',
	content VARCHAR(50) not null comment'选项内容',
    last_modify_time datetime comment'该数据最后修改时间',
    recorded_time datetime not null comment'录入数据时的时间',
    unique key(question_id,sort_order),
    unique key(question_id,content)
)CHARSET=utf8 COLLATE=utf8_general_ci comment'问题选项表';

/*正确答案表*/
DROP TABLE IF EXISTS `correct_answer`;
CREATE TABLE IF NOT EXISTS `correct_answer`(
	id int primary key auto_increment not null,
	question_id int not null comment '问题id，外键',
    correct_option_id int not null comment'正确答案选项id，外键',
    last_modify_time datetime comment'该数据最后修改时间',
    unique key(question_id,correct_option_id)
)CHARSET=utf8 COLLATE=utf8_general_ci comment'正确答案表';


/*问题标签关系表*/
DROP TABLE IF EXISTS `question_tag_relation`;
CREATE TABLE IF NOT EXISTS `question_tag_relation`(
	id int primary key auto_increment not null,
	question_id int not null comment '问题id，外键',
    tag_id int not null comment '标签id，外键',
    recorded_time datetime not null comment'录入数据时的时间',
    unique key(question_id,tag_id)
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'问题标签关系表';

/*问题审核记录表，系统管理员具有一票否决权*/
DROP TABLE IF EXISTS `question_verify_log`;
CREATE TABLE IF NOT EXISTS `question_verify_log`(
	id int primary key auto_increment not null,
	user_id int not null comment '用户id，外键',
    question_id int not null comment '问题id，外键',
    verify_type int not null default 1 comment '审核类型，1普通用户审核，2系统管理员审核，系统管理员具有一票否决权',
    recorded_time datetime not null comment'录入数据时的时间',
	`status` int not null default 1 comment '状态，1审核通过，2审核不通过，系统管理员具有一票否决权',
	unique key(user_id,question_id,verify_type)
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'问题审核记录表';



/*用户答题记录表*/
DROP TABLE IF EXISTS `user_answer_log`;
CREATE TABLE IF NOT EXISTS `user_answer_log`(
	id int primary key auto_increment not null,
	user_id int not null comment '用户id，外键',
	paper_id int comment '考卷id，如果不为空则表示答卷记录，如果为空表示单个答题记录，外键',
	question_id int comment '问题id，外键',
    option_id int comment'用户所填答案选项id，外键',
    answer_second int not null comment '答题花费的时间（秒）',
    recorded_time datetime not null comment'录入数据时的时间'
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'用户答题记录表';

/*考卷表*/
DROP TABLE IF EXISTS `paper`;
CREATE TABLE IF NOT EXISTS `paper`(
	id int primary key auto_increment not null,
	user_id int not null comment '录入人，外键',
    `name` VARCHAR(50) not null comment'考卷名称',
    `type` int not null default 1 comment '考卷类型，1历史真实考卷，2系统生成模拟考卷，3有奖答题',
	learning_phase_id int not null comment '答题应该需要的学历（学习阶段），外键',
	exam_id int not null comment '考试类型id，外键',
    points decimal(10,2) not null default 0 comment '问卷分数',
    gold decimal(10,2) not null default 0 comment '问卷奖励金币（只有有奖答题类型才用到这个字段）',
    area_id int comment'考试区域id，外键',
    answer_second int not null default 360 comment '答题应该需要的时间（秒）',
    paper_year datetime not null comment'考试年份',
    paper_month datetime not null comment'考试月份',
    recorded_time datetime not null comment'录入数据时的时间'
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'考卷表';

/*考卷题目表*/
DROP TABLE IF EXISTS `paper_question_relation`;
CREATE TABLE IF NOT EXISTS `paper_question_relation`(
	id int primary key auto_increment not null,
    paper_id int not null comment '考卷id，外键',
	question_id int not null comment '问题 id，外键',
	sort_order varchar(5) NOT NULL COMMENT '选项显示顺序，越小越靠前',
    recorded_time datetime not null comment'录入数据时的时间',
    unique key(paper_id,question_id)
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'考卷题目表';


/*状态数据维护表*/
DROP TABLE IF EXISTS `status_data`;
CREATE TABLE IF NOT EXISTS `status_data`(
	id int primary key auto_increment not null,
    `table_name` varchar(100) not null comment '表名',
    `column_name` varchar(100) not null comment '字段名',
    `module` int not null default 0 comment '模块,0全系统所有模块',
    `data_value` varchar(100) not null comment '值',
    `show_name` varchar(100) not null comment '显示名称',
	`sort_order` varchar(5) NOT NULL COMMENT '选项显示顺序，越小越靠前',
    last_modify_time datetime comment'该数据最后修改时间'
)CHARSET=utf8 COLLATE=utf8_general_ci COMMENT'状态数据维护表';

