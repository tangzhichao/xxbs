USE xxbs;

SET SQL_SAFE_UPDATES = 0;
delete from `permission`;
delete from `role`;
delete from `role_permission_relation`;

INSERT INTO `permission` VALUES(1,NULL,'所有权限','all',NULL);
INSERT INTO `permission` VALUES(2,1,'所有用户权限','all_cust',NULL);
INSERT INTO `permission` VALUES(3,1,'所有管理权限','all_manager',NULL);

INSERT INTO role VALUES(1,NULL,'用户端','cust');
INSERT INTO role VALUES(2,NULL,'管理端','manager');
INSERT INTO role VALUES(3,2,'超级管理员','super_manager');
INSERT INTO role VALUES(4,2,'普通管理员','common_manager');

INSERT INTO role_permission_relation VALUES(1,1,2);
INSERT INTO role_permission_relation VALUES(2,2,3);
INSERT INTO role_permission_relation VALUES(3,3,3);


select * from learning_phase;
delete from `learning_phase`;

INSERT INTO learning_phase VALUES(1,NULL,'小学','min',1);

id int primary key auto_increment not null,
    `table_name` varchar(100) not null comment '表名',
    `column_name` varchar(100) not null comment '字段名',
    `module` int not null default 0 comment '模块,0全系统所有模块',
    `data_value` varchar(100) not null comment '值',
    `show_name` varchar(100) not null comment '显示名称',
	`sort_order` varchar(5) NOT NULL COMMENT '选项显示顺序，越小越靠前',
    last_modify_time datetime comment'该数据最后修改时间'
0系统封号，1正常，2防盗号锁定，4删除
select * from status_data;
INSERT INTO status_data(`table_name`,`column_name`,`module`,`data_value`,`show_name`,`sort_order`,last_modify_time) VALUES('user','sex','user','0','男','1',now());
INSERT INTO status_data(`table_name`,`column_name`,`module`,`data_value`,`show_name`,`sort_order`,last_modify_time) VALUES('user','sex','user','1','女','2',now());

INSERT INTO status_data(`table_name`,`column_name`,`module`,`data_value`,`show_name`,`sort_order`,last_modify_time) VALUES('user','valid','user','1','正常','1',now());
INSERT INTO status_data(`table_name`,`column_name`,`module`,`data_value`,`show_name`,`sort_order`,last_modify_time) VALUES('user','valid','user','0','系统封号','2',now());
INSERT INTO status_data(`table_name`,`column_name`,`module`,`data_value`,`show_name`,`sort_order`,last_modify_time) VALUES('user','valid','user','2','防盗号锁定','3',now());
INSERT INTO status_data(`table_name`,`column_name`,`module`,`data_value`,`show_name`,`sort_order`,last_modify_time) VALUES('user','valid','user','3','删除','3',now());



