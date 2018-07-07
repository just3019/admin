DROP TABLE IF EXISTS account;

CREATE TABLE account
(
  id              INT NOT NULL AUTO_INCREMENT,
  username        VARCHAR(32)  DEFAULT NULL
  COMMENT '用户名',
  password        VARCHAR(100) DEFAULT NULL
  COMMENT '密码',
  phone           VARCHAR(20)  DEFAULT NULL
  COMMENT '手机号',
  email           VARCHAR(20)  DEFAULT NULL
  COMMENT '邮箱',
  union_id        VARCHAR(50)  DEFAULT NULL
  COMMENT '微信号',
  last_login_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
  COMMENT '最后登录时间',
  create_time     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  modify_time     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '编辑时间',
  PRIMARY KEY (id)
);

ALTER TABLE account
  COMMENT '用户表';

DROP TABLE IF EXISTS channel;

CREATE TABLE channel
(
  id          INT         NOT NULL AUTO_INCREMENT,
  uid         INT         NOT NULL DEFAULT 0
  COMMENT '用户id',
  package_id  INT         NOT NULL DEFAULT 0
  COMMENT '包id',
  no          VARCHAR(50) NOT NULL
  COMMENT '渠道编号',
  name        VARCHAR(50)          DEFAULT NULL
  COMMENT '渠道名',
  type        INT         NOT NULL DEFAULT 0
  COMMENT '渠道类型',
  stauts      INT         NOT NULL DEFAULT 1
  COMMENT '状态 0关闭 1开启',
  create_time TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
  modify_time TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

ALTER TABLE channel
  COMMENT '渠道';

DROP TABLE IF EXISTS real_time_data;

CREATE TABLE real_time_data
(
  id            INT         NOT NULL AUTO_INCREMENT,
  channel_no    VARCHAR(50) NOT NULL
  COMMENT '渠道编号',
  pay_price     BIGINT      NOT NULL DEFAULT 0
  COMMENT '真实支付金额',
  channel_price BIGINT      NOT NULL DEFAULT 0
  COMMENT '渠道支付金额',
  active        INT         NOT NULL DEFAULT 0
  COMMENT '激活数',
  pay           INT         NOT NULL DEFAULT 0
  COMMENT '支付数',
  create_time   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
  modity_time   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

ALTER TABLE real_time_data
  COMMENT '实时统计表';


DROP TABLE IF EXISTS banner;

CREATE TABLE banner
(
  id          INT NOT NULL AUTO_INCREMENT,
  package_id  INT NOT NULL DEFAULT 0
  COMMENT '包id',
  pic_url     VARCHAR(200) DEFAULT NULL
  COMMENT '图片url',
  url         VARCHAR(200) DEFAULT NULL
  COMMENT '跳转链接',
  `desc`      VARCHAR(200) DEFAULT NULL
  COMMENT '描述',
  sort        INT NOT NULL DEFAULT 9999
  COMMENT '排序',
  status      INT NOT NULL DEFAULT 1
  COMMENT '状态 0未使用 1在使用',
  create_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  modify_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

ALTER TABLE banner
  COMMENT '广告栏';


DROP TABLE IF EXISTS package;

CREATE TABLE package
(
  id   INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) DEFAULT NULL
  COMMENT '包名',
  url  VARCHAR(200) DEFAULT NULL
  COMMENT '包下载地址',
  pic  VARCHAR(200) DEFAULT NULL
  COMMENT '图片地址',
  PRIMARY KEY (id)
);

ALTER TABLE package
  COMMENT '包信息';


DROP TABLE IF EXISTS module;

CREATE TABLE module
(
  id         INT NOT NULL AUTO_INCREMENT,
  package_id INT NOT NULL DEFAULT 0
  COMMENT '包id',
  name       VARCHAR(50)  DEFAULT NULL
  COMMENT '模块名',
  icon       VARCHAR(200) DEFAULT NULL
  COMMENT 'icon',
  PRIMARY KEY (id)
);

ALTER TABLE module
  COMMENT '模块';


DROP TABLE IF EXISTS catalog;

CREATE TABLE catalog
(
  id         INT NOT NULL AUTO_INCREMENT,
  package_id INT          DEFAULT 0
  COMMENT '包id',
  name       VARCHAR(50)  DEFAULT NULL
  COMMENT '分类名',
  url        VARCHAR(200) DEFAULT NULL
  COMMENT '分类链接',
  sort       INT NOT NULL DEFAULT 9999
  COMMENT '排名',
  PRIMARY KEY (id)
);

ALTER TABLE catalog
  COMMENT '分类';


DROP TABLE IF EXISTS resource;

CREATE TABLE resource
(
  id          INT NOT NULL AUTO_INCREMENT,
  catalog_id  INT          DEFAULT 0
  COMMENT '分类id',
  type        INT          DEFAULT 0
  COMMENT '类型 1图片 2视频',
  `desc`      VARCHAR(500) DEFAULT NULL
  COMMENT '描述',
  name        VARCHAR(200) DEFAULT NULL
  COMMENT '名',
  url         VARCHAR(200) DEFAULT NULL
  COMMENT '列表图片地址',
  statistic   INT          DEFAULT 0
  COMMENT '资源点击次数',
  create_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  modify_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

ALTER TABLE resource
  COMMENT '资源';


DROP TABLE IF EXISTS resource_info;

CREATE TABLE resource_info
(
  id          INT NOT NULL AUTO_INCREMENT,
  resource_id INT          DEFAULT 0
  COMMENT '资源id',
  url         VARCHAR(200) DEFAULT NULL
  COMMENT '地址',
  sort        INT          DEFAULT 9999
  COMMENT '排序',
  create_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  modify_time TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

ALTER TABLE resource_info
  COMMENT '资源详细';

DROP TABLE IF EXISTS video_user;

CREATE TABLE video_user
(
  id           INT NOT NULL AUTO_INCREMENT,
  channel_no   VARCHAR(50)  DEFAULT NULL
  COMMENT '渠道编号',
  type         INT          DEFAULT 1
  COMMENT '类型 1:普通 2:vip 3:高级vip',
  cell_model   VARCHAR(50)  DEFAULT NULL
  COMMENT '手机型号',
  cell_version VARCHAR(50)  DEFAULT NULL
  COMMENT '系统版本',
  package_id   INT          DEFAULT 0
  COMMENT '包id',
  create_time  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  modify_time  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

ALTER TABLE video_user
  COMMENT '视频用户';

DROP TABLE IF EXISTS video_user_active;

CREATE TABLE video_user_active
(
  id            INT NOT NULL AUTO_INCREMENT,
  channel_no   VARCHAR(50)  DEFAULT NULL
  COMMENT '渠道编号',
  code          VARCHAR(20)  DEFAULT NULL
  COMMENT '激活码',
  video_user_id INT          DEFAULT 0
  COMMENT '视频用户id',
  status        INT          DEFAULT 0
  COMMENT '状态 0:未使用 1:已使用',
  create_time   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  modify_time   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

ALTER TABLE video_user_active
  COMMENT '激活码';


drop table if exists `order`;

/*==============================================================*/
/* Table: "order"                                               */
/*==============================================================*/
create table `order`
(
  id                   int not null,
  order_no             varchar(50) comment '订单编号',
  pay_no               varchar(50) comment '支付单号',
  price                float comment '支付金额',
  real_price           float comment '实付金额',
  create_time          timestamp,
  modify_time          timestamp,
  primary key (id)
);

alter table `order` comment '订单表';


