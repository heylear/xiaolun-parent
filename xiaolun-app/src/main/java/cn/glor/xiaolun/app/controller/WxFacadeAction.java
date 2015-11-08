package cn.glor.xiaolun.app.controller;

import cn.glor.xiaolun.app.controller.common.BaseAction;
import cn.glor.xiaolun.app.controller.common.SessionKeys;
import cn.glor.xiaolun.app.controller.common.SessionUtil;
import cn.glor.xiaolun.app.service.WxMpServiceHolder;
import me.chanjar.weixin.common.api.WxConsts;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by caosh on 2015/10/7.
 */
@Result(type = "redirect", name = "success", location = "${redirectUrl}")
public class WxFacadeAction extends BaseAction {

    private static Log log = LogFactory.getLog(WxFacadeAction.class);

    private String func;

    private String redirectUrl;

    @Autowired
    private WxMpServiceHolder wxMpServiceHolder;

    @Autowired
    private WxFacadeDispatcher wxFacadeDispatcher;

    public void setFunc(String func) {
        this.func = func;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Override
    public String execute() throws Exception {
        log.info("微信门面：func = " + func);

        if (SessionUtil.getSessionUser() == null) {
            String authorizationUrl = wxMpServiceHolder.getWxMpService().oauth2buildAuthorizationUrl(WxConsts.OAUTH2_SCOPE_USER_INFO, func);
            log.info("授权URL：" + authorizationUrl);

            redirectUrl = authorizationUrl;
        } else {
            // logged in
            redirectUrl = wxFacadeDispatcher.dispatch(func);
        }

        return SUCCESS;
    }
}
