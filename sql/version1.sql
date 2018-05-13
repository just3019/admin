drop table if exists account;

create table account
(
   id                   int not null auto_increment,
   username             varchar(32) default NULL comment '用户名',
   password             varchar(100) default NULL comment '密码',
   phone                varchar(20) default NULL comment '手机号',
   email                varchar(20) default NULL comment '邮箱',
   union_id             varchar(50) default NULL comment '微信号',
   last_login_time      timestamp default CURRENT_TIMESTAMP comment '最后登录时间',
   create_time          timestamp default CURRENT_TIMESTAMP comment '创建时间',
   modify_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '编辑时间',
   primary key (id)
);

alter table account comment '用户表';

drop table if exists channel;

create table channel
(
   id                   int not null auto_increment,
   uid                  int not null default 0 comment '用户id',
   package_id           int not null default 0 comment '包id',
   no                   varchar(50) not null comment '渠道编号',
   name                 varchar(50) default NULL comment '渠道名',
   type                 int not null default 0 comment '渠道类型',
   stauts               int not null default 1 comment '状态 0关闭 1开启',
   create_time          timestamp default CURRENT_TIMESTAMP,
   modify_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table channel comment '渠道';

drop table if exists real_time_data;

create table real_time_data
(
   id                   int not null auto_increment,
   channel_no           varchar(50) not null comment '渠道编号',
   pay_price            bigint not null default 0 comment '真实支付金额',
   channel_price        bigint not null default 0 comment '渠道支付金额',
   active               int not null default 0 comment '激活数',
   pay                  int not null default 0 comment '支付数',
   create_time          timestamp default CURRENT_TIMESTAMP,
   modity_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table real_time_data comment '实时统计表';


drop table if exists banner;

create table banner
(
   id                   int not null auto_increment,
   package_id           int not null default 0 comment '包id',
   pic_url              varchar(200) default NULL comment '图片url',
   url                  varchar(200) default NULL comment '跳转链接',
   `desc`               varchar(200) default NULL comment '描述',
   sort                 int not null default 9999 comment '排序',
   status               int not null default 1 comment '状态 0未使用 1在使用',
   create_time          timestamp default CURRENT_TIMESTAMP,
   modify_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table banner comment '广告栏';


drop table if exists package;

create table package
(
   id                   int not null auto_increment,
   name                 varchar(100) default NULL comment '包名',
   url                  varchar(200) default NULL comment '包下载地址',
   pic                  varchar(200) default NULL comment '图片地址',
   primary key (id)
);

alter table package comment '包信息';


drop table if exists module;

create table module
(
   id                   int not null auto_increment,
   package_id           int not null default 0 comment '包id',
   name                 varchar(50) default NULL comment '模块名',
   icon                 varchar(200) default NULL comment 'icon',
   primary key (id)
);

alter table module comment '模块';


drop table if exists catalog;

create table catalog
(
   id                   int not null auto_increment,
   package_id           int default 0 comment '包id',
   name                 varchar(50) default NULL comment '分类名',
   url                  varchar(200) default NULL comment '分类链接',
   sort                 int not null default 9999 comment '排名',
   primary key (id)
);

alter table catalog comment '分类';


drop table if exists resource;

create table resource
(
   id                   int not null auto_increment,
   catalog_id           int default 0 comment '分类id',
   type                 int default 0 comment '类型 1图片 2视频',
   `desc`               varchar(500) default NULL comment '描述',
   name                 varchar(200) default NULL comment '名',
   url                  varchar(200) default NULL comment '列表图片地址',
   statistic            int default 0 comment '资源点击次数',
   create_time          timestamp default CURRENT_TIMESTAMP,
   modify_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table resource comment '资源';


drop table if exists resource_info;

create table resource_info
(
   id                   int not null auto_increment,
   resource_id          int default 0 comment '资源id',
   url                  varchar(200) default NULL comment '地址',
   sort                 int default 9999 comment '排序',
   create_time          timestamp default CURRENT_TIMESTAMP,
   modify_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

alter table resource_info comment '资源详细';
