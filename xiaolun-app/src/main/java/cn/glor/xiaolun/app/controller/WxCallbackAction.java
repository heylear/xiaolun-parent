package cn.glor.xiaolun.app.controller;

import cn.glor.xiaolun.app.controller.common.BaseAction;
import cn.glor.xiaolun.app.service.WxMpLocationMessageHandler;
import cn.glor.xiaolun.app.service.WxMpServiceHolder;
import cn.glor.xiaolun.app.service.WxMpSubcribeMessageHandler;
import com.opensymphony.xwork2.ActionSupport;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * Created by heylear on 15/11/1.
 */
@ParentPackage("wx")
@Result(name = "success", type = "plainText")
public class WxCallbackAction extends BaseAction {

    @Autowired
    private WxMpServiceHolder wxMpServiceHolder;

    @Autowired
    private WxMpLocationMessageHandler wxMpLocationMessageHandler;

    @Autowired
    private WxMpSubcribeMessageHandler wxMpSubcribeMessageHandler;


    String timestamp;

    String nonce;

    String signature;

    String echostr;

    @Action("callback")
    public void callback() throws Exception{

        String result = "";

        if (HttpMethod.POST.name().equals(ServletActionContext.getRequest().getMethod())) {

            try {
                WxMpMessageRouter router = new WxMpMessageRouter(wxMpServiceHolder.getWxMpService());

                router.rule().msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_LOCATION).handler(wxMpLocationMessageHandler).end();

                router.rule().msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_SUBSCRIBE).handler(wxMpSubcribeMessageHandler).end();

                WxMpXmlMessage wxMpXmlMessage = WxMpXmlMessage.fromXml(request.getInputStream());

                WxMpXmlOutMessage wxMpXmlOutMessage = router.route(wxMpXmlMessage);

                if(wxMpXmlOutMessage != null)
                    result = router.route(wxMpXmlMessage).toXml();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            boolean checkSignature = wxMpServiceHolder.getWxMpService().checkSignature(timestamp, nonce, signature);
            if (checkSignature) {
                result = echostr;
            }
        }
        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().write(result);
        response.getWriter().flush();
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }
}
