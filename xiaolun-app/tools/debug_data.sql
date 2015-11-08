update tm_config set cfg_value = 'wx3022d93a871e5aca' where cfg_key = 'wx.appId';
update tm_config set cfg_value = '906d9e7d45b6eabe10dfc306b69fc3ec' where cfg_key = 'wx.appSecret';
update tm_config set cfg_value = 'http://192.168.11.100/wx-redirect.action' where cfg_key = 'wx.oauth2RedirectUrl';
update tm_config set cfg_value = 'http://192.168.11.100/' where cfg_key = 'site.path';

update tm_wx_tplmsg set tpl_id = 'daTZqNVi_Zbjd_v-7inuwBUiJFmxkm-em1UWqKkGfLM' where tpl_name = 'pushCreateConsultOrderToCustomer';
update tm_wx_tplmsg set tpl_id = 'jqB7vjVUcuqC-hVKI40KdW6NmWc2Yk_LmgF21zC8vWA' where tpl_name = 'pushCreateConsultOrderToSeller';
update tm_wx_tplmsg set tpl_id = 'QepGeSKn0hTcH7AiBpiLgCg2yMXJXe9iYaLuRhXylc4' where tpl_name = 'pushCatchConsultOrderToCustomer';
update tm_wx_tplmsg set tpl_id = 'QepGeSKn0hTcH7AiBpiLgCg2yMXJXe9iYaLuRhXylc4' where tpl_name = 'pushCatchConsultOrderToSeller';

insert into tt_mp_user (openid, unionid, nickname, sex, city, province, country, language, headimgurl, is_subscribe, subscribe_time, entity_status) values (
    'oVvQ4xEsrJllFhfM_5ygcskDQzJY', 'abcd', '书豪', '男', '浦东', '上海', '中国', 'zh',
    'http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0',
    1, now(), 1
);
insert into tt_mp_user (openid, unionid, nickname, sex, city, province, country, language, headimgurl, is_subscribe, subscribe_time, entity_status)
values (
    'oVvQ4xD1u97hl6okgAcHSz0bYOz8', 'abcde', 'Jac', '女', '长宁', '上海', '中国', 'zh',
    'http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0',
    1, now(), 1
);