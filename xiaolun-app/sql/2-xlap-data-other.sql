/*==============================================================*/
/* Table: tm_wx_menu                                            */
/*==============================================================*/
delete from tm_wx_menu where 1 = 1;
insert into tm_wx_menu (entity_id, menu_name, menu_type, menu_key, menu_url, parent_id, entity_status, create_time, create_user, update_time, update_user) VALUES (1,'找顾问','view',null,'consult',0,'1',now(),0,now(),0);
insert into tm_wx_menu (entity_id, menu_name, menu_type, menu_key, menu_url, parent_id, entity_status, create_time, create_user, update_time, update_user) VALUES (2,'找车辆',null,null,null,0,'1',now(),0,now(),0);
insert into tm_wx_menu (entity_id, menu_name, menu_type, menu_key, menu_url, parent_id, entity_status, create_time, create_user, update_time, update_user) VALUES (3,'我的小轮',null,null,null,0,'1',now(),0,now(),0);
insert into tm_wx_menu (entity_id, menu_name, menu_type, menu_key, menu_url, parent_id, entity_status, create_time, create_user, update_time, update_user) VALUES (4,'附近好车','view',null,'carlist',2,'1',now(),0,now(),0);
insert into tm_wx_menu (entity_id, menu_name, menu_type, menu_key, menu_url, parent_id, entity_status, create_time, create_user, update_time, update_user) VALUES (5,'我的车辆','view',null,'mycenter',2,'1',now(),0,now(),0);
insert into tm_wx_menu (entity_id, menu_name, menu_type, menu_key, menu_url, parent_id, entity_status, create_time, create_user, update_time, update_user) VALUES (6,'我的顾问','view',null,'customer',3,'1',now(),0,now(),0);
insert into tm_wx_menu (entity_id, menu_name, menu_type, menu_key, menu_url, parent_id, entity_status, create_time, create_user, update_time, update_user) VALUES (7,'顾问专区','view',null,'seller',3,'1',now(),0,now(),0);
insert into tm_wx_menu (entity_id, menu_name, menu_type, menu_key, menu_url, parent_id, entity_status, create_time, create_user, update_time, update_user) VALUES (8,'注册顾问','view',null,'register',3,'1',now(),0,now(),0);

/*==============================================================*/
/* Table: config                                                */
/*==============================================================*/
delete from tm_config where 1 = 1;
insert into tm_config (cfg_key, cfg_value) values ('wx.appID', 'wx6db88ec4287a4ebc');
insert into tm_config (cfg_key, cfg_value) values ('wx.appSecret', '4b20ccd348d5290ccf4b6587f8dda047');
insert into tm_config (cfg_key, cfg_value) values ('wx.oauth2RedirectUrl', 'http://wx.carwor.com/wxglor/wx-redirect.action');
insert into tm_config (cfg_key, cfg_value) values ('wx.token', '015685bfdd817d9b605ed1c7683d7ee3');
insert into tm_config (cfg_key, cfg_value) values ('file.rootDir', '/glor/fileserver/download');
insert into tm_config (cfg_key, cfg_value) values ('site.path', 'http://wx.carwor.com/');
insert into tm_config (cfg_key, cfg_value) values ('loc.defX', '31.283995');
insert into tm_config (cfg_key, cfg_value) values ('loc.defY', '121.54113');
insert into tm_config (cfg_key, cfg_value) values ('loc.nearbyredius', '50');

/*==============================================================*/
/* Table: tm_wx_tplmsg                                                 */
/*==============================================================*/
truncate tm_wx_tplmsg;
insert into `tm_wx_tplmsg` (tpl_name, tpl_id) values ('pushCreateConsultOrderToCustomer', '6GODjqrQWK6bD_ze8D4dewCl8RMGFqFxOaRptqKyMQs');
insert into `tm_wx_tplmsg` (tpl_name, tpl_id) values ('pushCreateConsultOrderToSeller', 'QiFa07a3CXtMlm6K_4Ie420ct0HYEFfEAWQdCw3erfc');
insert into `tm_wx_tplmsg` (tpl_name, tpl_id) values ('pushCatchConsultOrderToCustomer', 'Nj1_krGOXOkmR9mDnEQ4M0NGdsojuVoDayYZ2KHyB8g');
insert into `tm_wx_tplmsg` (tpl_name, tpl_id) values ('pushCatchConsultOrderToSeller', 'Nj1_krGOXOkmR9mDnEQ4M0NGdsojuVoDayYZ2KHyB8g');

/*==============================================================*/
/* Table: tt_job                                                 */
/*==============================================================*/
truncate tt_job;
insert into tt_job (job_name, job_group, bean_name, method_name, cron_expr) values ('refreshWxMpAccessToken', 'wx', 'wxPushService', 'refreshWxMpAccessToken', '0 0 0/1 * * ?');
