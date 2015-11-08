/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/11/7 23:47:21                           */
/*==============================================================*/


drop index unique_cfg_key on tm_config;

drop table if exists tm_config;

drop table if exists tm_region;

drop table if exists tm_sub_brand;

drop table if exists tm_wx_menu;

drop index unique_name on tm_wx_tplmsg;

drop table if exists tm_wx_tplmsg;

drop table if exists tr_car_pic;

drop index unique_consult_seller on tr_consult_seller;

drop index index_customer_id on tr_consult_seller;

drop index index_seller_id on tr_consult_seller;

drop index index_consult_id on tr_consult_seller;

drop table if exists tr_consult_seller;

drop index index_sub_brand_id on tr_consult_sub_brand;

drop index index_consult_id on tr_consult_sub_brand;

drop table if exists tr_consult_sub_brand;

drop index unique_seller_brand on tr_seller_expert_brand;

drop table if exists tr_seller_expert_brand;

drop table if exists tt_action;

drop table if exists tt_attach;

drop table if exists tt_consult_order;

drop index unique_group_name on tt_job;

drop table if exists tt_job;

drop table if exists tt_location;

drop index index_wx_open_id on tt_mp_user;

drop index index_phone_number on tt_mp_user;

drop index index_wx_union_id on tt_mp_user;

drop table if exists tt_mp_user;

drop table if exists tt_question;

drop table if exists tt_seller_account;

/*==============================================================*/
/* Table: tm_config                                             */
/*==============================================================*/
create table tm_config
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   cfg_key              varchar(255) not null comment '键',
   cfg_value            varchar(1024) comment '值',
   entity_status        varchar(1) not null default '1' comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          datetime comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          datetime comment '更新时间',
   update_user          bigint comment '更新人',
   primary key (entity_id)
);

alter table tm_config comment '配置表';

/*==============================================================*/
/* Index: unique_cfg_key                                        */
/*==============================================================*/
create unique index unique_cfg_key on tm_config
(
   cfg_key
);

/*==============================================================*/
/* Table: tm_region                                             */
/*==============================================================*/
create table tm_region
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   region_code          varchar(10) comment '区域代码',
   region_name          varchar(128) comment '区域名称',
   region_py_short      varchar(32) comment '区域简拼',
   region_py            varchar(64) comment '区域全拼',
   parent_code          varchar(10) comment '所属区域代码',
   biz_opened           int not null default 0 comment '业务开放状态，0未开放，1已开放',
   entity_status        varchar(1) comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          datetime comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          datetime comment '更新时间',
   update_user          bigint comment '更新人',
   primary key (entity_id)
);

alter table tm_region comment '地区基础数据表';

/*==============================================================*/
/* Table: tm_sub_brand                                          */
/*==============================================================*/
create table tm_sub_brand
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   bd_name              varchar(32) not null comment '名称',
   pinyin_abbr          varchar(32) not null comment '拼音简称',
   entity_status        varchar(1) not null default '1' comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          datetime comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          datetime comment '更新时间',
   update_user          bigint comment '更新人',
   primary key (entity_id)
);

alter table tm_sub_brand comment '子品牌表';

/*==============================================================*/
/* Table: tm_wx_menu                                            */
/*==============================================================*/
create table tm_wx_menu
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   menu_name            varchar(32) comment '菜单名称',
   menu_type            varchar(32) comment '菜单类型',
   menu_key             varchar(32) comment '菜单代码',
   menu_url             varchar(255) comment '菜单跳转url',
   parent_id            bigint comment '父菜单ID',
   entity_status        varchar(1) comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          datetime comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          datetime comment '更新时间',
   update_user          bigint comment '更新人',
   primary key (entity_id)
);

alter table tm_wx_menu comment '小轮微信菜单表';

/*==============================================================*/
/* Table: tm_wx_tplmsg                                          */
/*==============================================================*/
create table tm_wx_tplmsg
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   tpl_name             varchar(64) not null comment '模板名称（系统自定义）',
   tpl_id               varchar(64) not null comment '模板ID（微信定义）',
   entity_status        varchar(1) not null default '1' comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          datetime comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          datetime comment '更新时间',
   update_user          bigint comment '更新人',
   primary key (entity_id)
);

alter table tm_wx_tplmsg comment '微信模板消息配置';

/*==============================================================*/
/* Index: unique_name                                           */
/*==============================================================*/
create unique index unique_name on tm_wx_tplmsg
(
   tpl_name
);

/*==============================================================*/
/* Table: tr_car_pic                                            */
/*==============================================================*/
create table tr_car_pic
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   mem_user_id          bigint comment '所属客户ID',
   attach_id            bigint comment '图片文件ID',
   entity_status        varchar(1) comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          datetime comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          datetime comment '更新时间',
   update_user          bigint comment '更新人',
   primary key (entity_id)
);

alter table tr_car_pic comment '客户车型图片表';

/*==============================================================*/
/* Table: tr_consult_seller                                     */
/*==============================================================*/
create table tr_consult_seller
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   consult_id           bigint not null comment '顾问单id',
   seller_id            bigint not null comment '销售顾问id',
   customer_id          bigint not null comment '用户id（优化查询冗余字段）',
   entity_status        varchar(1) not null default '1' comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          datetime comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          datetime comment '更新时间',
   update_user          bigint comment '更新人',
   primary key (entity_id)
);

alter table tr_consult_seller comment '顾问单顾问抢单关联表';

/*==============================================================*/
/* Index: index_consult_id                                      */
/*==============================================================*/
create index index_consult_id on tr_consult_seller
(
   consult_id
);

/*==============================================================*/
/* Index: index_seller_id                                       */
/*==============================================================*/
create index index_seller_id on tr_consult_seller
(
   seller_id
);

/*==============================================================*/
/* Index: index_customer_id                                     */
/*==============================================================*/
create index index_customer_id on tr_consult_seller
(
   customer_id
);

/*==============================================================*/
/* Index: unique_consult_seller                                 */
/*==============================================================*/
create unique index unique_consult_seller on tr_consult_seller
(
   consult_id,
   seller_id
);

/*==============================================================*/
/* Table: tr_consult_sub_brand                                  */
/*==============================================================*/
create table tr_consult_sub_brand
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   consult_id           bigint not null comment '顾问单id',
   sub_brand_id         bigint not null comment '子品牌id',
   entity_status        varchar(1) not null default '1' comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          datetime comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          datetime comment '更新时间',
   update_user          bigint comment '更新人',
   primary key (entity_id)
);

alter table tr_consult_sub_brand comment '顾问单意向品牌关联表';

/*==============================================================*/
/* Index: index_consult_id                                      */
/*==============================================================*/
create index index_consult_id on tr_consult_sub_brand
(
   consult_id
);

/*==============================================================*/
/* Index: index_sub_brand_id                                    */
/*==============================================================*/
create index index_sub_brand_id on tr_consult_sub_brand
(
   sub_brand_id
);

/*==============================================================*/
/* Table: tr_seller_expert_brand                                */
/*==============================================================*/
create table tr_seller_expert_brand
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   seller_id            bigint not null comment '销售顾问id',
   sub_brand_id         bigint not null comment '子品牌id',
   entity_status        varchar(1) not null default '1' comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          datetime comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          datetime comment '更新时间',
   update_user          bigint comment '更新人',
   primary key (entity_id)
);

alter table tr_seller_expert_brand comment '销售顾问擅长品牌关联表';

/*==============================================================*/
/* Index: unique_seller_brand                                   */
/*==============================================================*/
create unique index unique_seller_brand on tr_seller_expert_brand
(
   seller_id,
   sub_brand_id
);

/*==============================================================*/
/* Table: tt_action                                             */
/*==============================================================*/
create table tt_action
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   act_type             varchar(1) comment '动态类型(1:我想坐坐 2:我想试试 3:我想买)',
   dest_user_id         bigint comment '点赞人',
   act_user_id          bigint comment '被点赞人',
   entity_status        varchar(1) comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          datetime comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          datetime comment '更新时间',
   update_user          bigint comment '更新人',
   primary key (entity_id)
);

alter table tt_action comment '个人中心动态表';

/*==============================================================*/
/* Table: tt_attach                                             */
/*==============================================================*/
create table tt_attach
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   attach_name          varchar(128) comment '文件名称',
   attach_type          varchar(64) comment '文件类型',
   attach_uri           varchar(128) comment '文件地址',
   attach_note          varchar(128) comment '文件备注',
   entity_status        varchar(1) comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          date comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          date comment '更新时间',
   update_user          bigint comment '更新人',
   primary key (entity_id)
);

alter table tt_attach comment '文件表';

/*==============================================================*/
/* Table: tt_consult_order                                      */
/*==============================================================*/
create table tt_consult_order
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   customer_id          bigint not null comment '用户id',
   city_id              bigint not null comment '城市id',
   wanted_consultants   int not null default 1 comment '想找顾问数量',
   description          varchar(255) comment '详情描述',
   entity_status        varchar(1) not null default '1' comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          datetime comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          datetime comment '更新时间',
   update_user          bigint comment '更新人',
   bak2                 varchar(1),
   bak3                 varchar(1),
   bak4                 varchar(1),
   bak1                 varchar(1),
   bak5                 varchar(1),
   primary key (entity_id)
);

alter table tt_consult_order comment '顾问单表';

/*==============================================================*/
/* Table: tt_job                                                */
/*==============================================================*/
create table tt_job
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   job_name             varchar(64) not null comment '任务名称',
   job_group            varchar(64) not null comment '任务组',
   bean_name            varchar(64) not null comment '容器内bean名称',
   method_name          varchar(64) not null comment 'bean的方法名',
   cron_expr            varchar(32) not null comment 'Cron表达式',
   enabled              bit not null default 1 comment '是否启用',
   entity_status        varchar(1) not null default '1' comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          datetime comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          datetime comment '更新时间',
   update_user          bigint comment '更新人',
   primary key (entity_id)
);

alter table tt_job comment '定时任务表';

/*==============================================================*/
/* Index: unique_group_name                                     */
/*==============================================================*/
create index unique_group_name on tt_job
(
   job_name,
   job_group
);

/*==============================================================*/
/* Table: tt_location                                           */
/*==============================================================*/
create table tt_location
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   mem_user_id          bigint comment '用户id',
   cur_loc_x            double comment '当前经度',
   cur_loc_y            double comment '当前纬度',
   pre_loc_x            double comment '上次经度(冗余)',
   pre_loc_y            double comment '上次纬度(冗余)',
   cur_prov             varchar(16) comment '当前省份代码',
   cur_city             varchar(16) comment '当前城市代码',
   entity_status        varchar(1) comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          datetime comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          datetime comment '更新时间',
   update_user          bigint comment '更新人',
   primary key (entity_id)
);

alter table tt_location comment '地理位置';

/*==============================================================*/
/* Table: tt_mp_user                                            */
/*==============================================================*/
create table tt_mp_user
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   openid               varchar(64) comment '用户的标识，对当前公众号唯一',
   unionid              varchar(64) comment '只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段',
   nickname             varchar(64) comment '用户的昵称',
   sex                  varchar(1) comment '姓别(1:男 2:女 0:未知)',
   city                 varchar(64) comment '用户所在城市',
   province             varchar(64) comment '用户所在省份',
   country              varchar(64) comment '用户所在国家',
   language             varchar(10) comment '用户的语言，简体中文为zh_CN',
   headimgurl           varchar(255) comment '用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。',
   is_subscribe         varchar(1) comment '用户是否已关注(0: 未关注 1: 已关注)',
   subscribe_time       datetime comment '用户关注时间',
   remark               text comment '公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注',
   groupid              varchar(20) comment '用户所在的分组ID',
   phone_number         varchar(32) comment '手机号',
   password_digest      varchar(32) comment '密码MD5',
   real_name            varchar(16) comment '真实姓名',
   entity_status        varchar(1) comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          datetime comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          datetime comment '更新时间',
   update_user          bigint comment '更新人',
   primary key (entity_id)
);

alter table tt_mp_user comment '用户表(目前仅有微信)';

/*==============================================================*/
/* Index: index_wx_union_id                                     */
/*==============================================================*/
create unique index index_wx_union_id on tt_mp_user
(
   unionid
);

/*==============================================================*/
/* Index: index_phone_number                                    */
/*==============================================================*/
create unique index index_phone_number on tt_mp_user
(
   phone_number
);

/*==============================================================*/
/* Index: index_wx_open_id                                      */
/*==============================================================*/
create unique index index_wx_open_id on tt_mp_user
(
   openid
);

/*==============================================================*/
/* Table: tt_question                                           */
/*==============================================================*/
create table tt_question
(
   entity_id            bigint not null auto_increment comment '实体逻辑主键，自增',
   post_user_id         bigint comment '提问/回答人',
   dest_user_id         bigint comment '提问车辆所属人',
   parent_id            bigint comment '父问题id, 如为新问题为0',
   quest_text           text comment '问题/回复内容',
   quest_state          varchar(1) comment '是否已回答(1:已回答 0:未回答)',
   entity_status        varchar(1) comment '实体状态(1:有效 0:无效) 仅做逻辑删除使用',
   create_time          datetime comment '创建时间',
   create_user          bigint comment '创建人',
   update_time          datetime comment '更新时间',
   update_user          bigint comment '更新人',
   primary key (entity_id)
);

alter table tt_question comment '问题表';

/*==============================================================*/
/* Table: tt_seller_account                                     */
/*==============================================================*/
create table tt_seller_account
(
   entity_id            bigint not null comment '等于mp_user表主键',
   birthday             date comment '出生日期',
   employer             varchar(128) comment '当前工作单位',
   work_phone           varchar(32) comment '工作电话',
   served_brand_id      bigint not null comment '服务品牌id',
   description          varchar(512) comment '自我介绍',
   consult_count_this_week int not null default 0 comment '本周内抢顾问单成功次数',
   region_id            bigint not null comment '地区id',
   audit_status         int not null default 0 comment '审核状态：0未审核，1审核通过，2审核未通过，3被屏蔽',
   primary key (entity_id)
);

alter table tt_seller_account comment '销售顾问子表';

