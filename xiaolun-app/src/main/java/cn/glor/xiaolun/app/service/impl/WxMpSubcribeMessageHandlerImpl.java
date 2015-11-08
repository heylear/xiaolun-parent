package cn.glor.xiaolun.app.service.impl;

import cn.glor.xiaolun.app.entity.MpUserEntity;
import cn.glor.xiaolun.app.repository.MpUserRepository;
import cn.glor.xiaolun.app.service.WxMpSubcribeMessageHandler;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Created by heylear on 15/11/1.
 */
@Service
public class WxMpSubcribeMessageHandlerImpl implements WxMpSubcribeMessageHandler {

    protected final static Logger logger = Logger.getLogger(WxMpSubcribeMessageHandlerImpl.class);

    @Autowired
    private MpUserRepository mpUserRepository;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        WxMpUser wxMpUser = wxMpService.userInfo(wxMpXmlMessage.getFromUserName(), null);

        try {
            mpUserRepository.saveOrUpdateMpUser(wxMpUser);
        } catch (Exception e) {
            logger.debug(e.getMessage(),e);
        }

        return null;
    }
}
