package cn.glor.xiaolun.app.controller;

import cn.glor.xiaolun.app.controller.common.BaseAction;
import cn.glor.xiaolun.app.controller.common.SessionUtil;
import cn.glor.xiaolun.app.repository.MpUserRepository;
import cn.glor.xiaolun.app.service.WxMpServiceHolder;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by caosh on 2015/10/7.
 */
@Result(type = "redirect", name = "success", location = "${redirectUrl}")
public class WxRedirectAction extends BaseAction {

    private String code;

    private String state;

    private String redirectUrl;

    @Autowired
    private WxMpServiceHolder wxMpServiceHolder;

    @Autowired
    private WxFacadeDispatcher wxFacadeDispatcher;

    @Autowired
    private MpUserRepository mpUserRepository;

    public void setCode(String code) {
        this.code = code;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Override
    public String execute() throws Exception {
        log.info(String.format("微信重定向：code = %s, state = %s", code, state));

        WxMpOAuth2AccessToken accessToken = wxMpServiceHolder.getWxMpService().oauth2getAccessToken(code);
        log.info("获取到微信网页授权token：" + accessToken.toString());

        WxMpUser wxMpUser = wxMpServiceHolder.getWxMpService().oauth2getUserInfo(accessToken, null);
        log.info("获取到微信用户信息：" + wxMpUser.toString());

        try {
            SessionUtil.setSessionUser(mpUserRepository.saveOrUpdateMpUser(wxMpUser));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        redirectUrl = wxFacadeDispatcher.dispatch(state);

        return SUCCESS;

    }
}
