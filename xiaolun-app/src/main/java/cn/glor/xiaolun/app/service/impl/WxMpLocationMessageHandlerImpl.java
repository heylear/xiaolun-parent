package cn.glor.xiaolun.app.service.impl;

import cn.glor.xiaolun.app.repository.LocationRepository;
import cn.glor.xiaolun.app.service.WxMpLocationMessageHandler;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by heylear on 15/11/1.
 */
@Service
public class WxMpLocationMessageHandlerImpl implements WxMpLocationMessageHandler {
    final static Logger logger = Logger.getLogger(WxMpLocationMessageHandlerImpl.class);

    @Autowired
    LocationRepository locationRepository;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        try{
            locationRepository.saveOrUpdateLoction(wxMpXmlMessage.getFromUserName(), wxMpXmlMessage.getLatitude(), wxMpXmlMessage.getLongitude());
        }catch (Exception e){
            logger.debug(e.getMessage(),e);
        }
        return null;
    }
}
