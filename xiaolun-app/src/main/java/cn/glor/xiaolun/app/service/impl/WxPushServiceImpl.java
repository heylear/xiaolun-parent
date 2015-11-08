package cn.glor.xiaolun.app.service.impl;

import cn.glor.xiaolun.app.dto.ConsultSellerDto;
import cn.glor.xiaolun.app.entity.ConsultOrderEntity;
import cn.glor.xiaolun.app.entity.ConsultOrderSellerRelation;
import cn.glor.xiaolun.app.entity.SellerAccountEntity;
import cn.glor.xiaolun.app.entity.WxTemplateMessageEntity;
import cn.glor.xiaolun.app.repository.ConfigRepository;
import cn.glor.xiaolun.app.repository.ConsultOrderRepository;
import cn.glor.xiaolun.app.repository.SellerAccountRepository;
import cn.glor.xiaolun.app.repository.WxTemplateMessageInfoRepository;
import cn.glor.xiaolun.app.service.WxMpServiceHolder;
import cn.glor.xiaolun.app.service.WxPushService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.WxMpTemplateMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by caosh on 2015/10/26.
 */
@Service("wxPushService")
public class WxPushServiceImpl implements WxPushService {

    private static Log log = LogFactory.getLog(WxPushServiceImpl.class);

    @Autowired
    private ConsultOrderRepository consultOrderRepository;

    @Autowired
    private SellerAccountRepository sellerAccountRepository;

    @Autowired
    private WxMpServiceHolder wxMpServiceHolder;

    @Autowired
    private WxTemplateMessageInfoRepository wxTemplateMessageInfoRepository;

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private ResourceBundleMessageSource messageSource;

    // TODO: move out!
    public void refreshWxMpAccessToken() {
        try {
            String accessToken = wxMpServiceHolder.getWxMpService().getAccessToken(true);
            log.info("刷新微信accessToken：" + accessToken);
        } catch (WxErrorException e) {
            log.error("刷新微信accessToken失败", e);
        }
    }

    /**
     * 发送微信模板消息
     *
     * @param templateMessage 模板消息
     * @see <a href="http://mp.weixin.qq.com/debug/cgi-bin/readtmpl?t=tmplmsg/faq_tmpl">模板消息接口文档</a>
     */
    private void pushMessage(WxMpTemplateMessage templateMessage) throws WxErrorException {
        wxMpServiceHolder.getWxMpService().templateSend(templateMessage);
    }

    @Override
    public void pushCreateConsultOrderToCustomer(Object consultId) {
        log.info("执行顾问单下单成功推送：consultId=" + consultId);

        WxTemplateMessageEntity templateMessageInfo = wxTemplateMessageInfoRepository.findByName("pushCreateConsultOrderToCustomer");
        if (templateMessageInfo == null) {
            log.error("模板消息信息未找到：pushCreateConsultOrderToCustomer");
            return;
        }

        ConsultOrderEntity consultOrder = consultOrderRepository.get((Long) consultId);

        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(templateMessageInfo.getTplId());
        templateMessage.setUrl(configRepository.getValue("site.path") + "/wx-facade.action?func=customerTypeConsultId" + consultId);
        templateMessage.setToUser(consultOrder.getCustomer().getOpenId());

        List<WxMpTemplateData> templateDataList = new ArrayList<>();

        String firstData = messageSource.getMessage("push.CreateConsultOrderToCustomer.first",
                new Object[]{consultOrder.getCustomer().getRealName()}, Locale.CHINA);
        String remarkData = messageSource.getMessage("push.CreateConsultOrderToCustomer.remark",
                new Object[]{consultOrder.getCity().getRegionName(), consultOrder.getWantedConsultants()}, Locale.CHINA);

        templateDataList.add(new WxMpTemplateData("first", firstData));
        templateDataList.add(new WxMpTemplateData("keyword1", String.valueOf(consultOrder.getEntityId())));
        templateDataList.add(new WxMpTemplateData("keyword2", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new Date(consultOrder.getCreateTime().getTime()))));
        templateDataList.add(new WxMpTemplateData("remark", remarkData));

        templateMessage.setDatas(templateDataList);
        try {
            pushMessage(templateMessage);
        } catch (WxErrorException e) {
            log.error("推送失败：", e);
        }
    }

    @Override
    public void pushCreateConsultOrderToSeller(Object parameter) {
        log.info("执行顾问单抢单提醒推送：parameter=" + parameter);

        WxTemplateMessageEntity templateMessageInfo = wxTemplateMessageInfoRepository.findByName("pushCreateConsultOrderToSeller");
        if (templateMessageInfo == null) {
            log.error("模板消息信息未找到：pushCreateConsultOrderToSeller");
            return;
        }

        ConsultSellerDto consultSellerDto = (ConsultSellerDto) parameter;
        ConsultOrderEntity consultOrder = consultOrderRepository.get(consultSellerDto.getConsultId());
        SellerAccountEntity seller = sellerAccountRepository.get(consultSellerDto.getSellerId());

        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(templateMessageInfo.getTplId());
        templateMessage.setUrl(configRepository.getValue("site.path") + "/wx-facade.action?func=sellerTypeConsultId" + consultSellerDto.getConsultId());
        templateMessage.setToUser(seller.getOpenId());

        List<WxMpTemplateData> templateDataList = new ArrayList<>();

        String firstData = messageSource.getMessage("push.CreateConsultOrderToSeller.first",
                new Object[]{seller.getRealName()}, Locale.CHINA);
        String remarkData = messageSource.getMessage("push.CreateConsultOrderToSeller.remark",
                new Object[]{consultOrder.getCustomer().getRealName()}, Locale.CHINA);

        templateDataList.add(new WxMpTemplateData("first", firstData));
        templateDataList.add(new WxMpTemplateData("keyword1", String.valueOf(consultOrder.getEntityId())));
        templateDataList.add(new WxMpTemplateData("keyword2", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new Date(consultOrder.getCreateTime().getTime()))));
        templateDataList.add(new WxMpTemplateData("remark", remarkData));

        templateMessage.setDatas(templateDataList);
        try {
            pushMessage(templateMessage);
        } catch (WxErrorException e) {
            log.error("推送失败：", e);
        }
    }

    @Override
    public void pushCatchConsultOrderToCustomer(Object relationId) {
        log.info("执行顾问单抢单成功推送（客户）：relationId=" + relationId);

        WxTemplateMessageEntity templateMessageInfo = wxTemplateMessageInfoRepository.findByName("pushCatchConsultOrderToCustomer");
        if (templateMessageInfo == null) {
            log.error("模板消息信息未找到：pushCatchConsultOrderToCustomer");
            return;
        }

        ConsultOrderSellerRelation relationEntity = consultOrderRepository.getSellerRelation((Long) relationId);
        ConsultOrderEntity consultOrder = consultOrderRepository.get(relationEntity.getConsultId());
        SellerAccountEntity seller = sellerAccountRepository.get(relationEntity.getSellerId());

        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(templateMessageInfo.getTplId());
        templateMessage.setUrl(configRepository.getValue("site.path") + "/wx-facade.action?func=customerTypeSellerId" + seller.getEntityId());
        templateMessage.setToUser(consultOrder.getCustomer().getOpenId());

        List<WxMpTemplateData> templateDataList = new ArrayList<>();

        String firstData = messageSource.getMessage("push.CatchConsultOrderToCustomer.first",
                new Object[]{consultOrder.getCustomer().getRealName()}, Locale.CHINA);
        String remarkData = messageSource.getMessage("push.CatchConsultOrderToCustomer.remark",
                new Object[]{}, Locale.CHINA);

        templateDataList.add(new WxMpTemplateData("first", firstData));
        templateDataList.add(new WxMpTemplateData("keyword1", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new Date(relationEntity.getCreateTime().getTime()))));
        templateDataList.add(new WxMpTemplateData("keyword2", seller.getRegion().getRegionName()));
        templateDataList.add(new WxMpTemplateData("keyword3", seller.getRealName()));
        templateDataList.add(new WxMpTemplateData("remark", remarkData));

        templateMessage.setDatas(templateDataList);
        try {
            pushMessage(templateMessage);
        } catch (WxErrorException e) {
            log.error("推送失败：", e);
        }
    }

    @Override
    public void pushCatchConsultOrderToSeller(Object relationId) {
        log.info("执行顾问单抢单成功推送（顾问）：relationId=" + relationId);

        WxTemplateMessageEntity templateMessageInfo = wxTemplateMessageInfoRepository.findByName("pushCatchConsultOrderToSeller");
        if (templateMessageInfo == null) {
            log.error("模板消息信息未找到：pushCatchConsultOrderToSeller");
            return;
        }

        ConsultOrderSellerRelation relationEntity = consultOrderRepository.getSellerRelation((Long) relationId);
        ConsultOrderEntity consultOrder = consultOrderRepository.get(relationEntity.getConsultId());
        SellerAccountEntity seller = sellerAccountRepository.get(relationEntity.getSellerId());

        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(templateMessageInfo.getTplId());
        templateMessage.setUrl(configRepository.getValue("site.path") + "/wx-facade.action?func=sellerTypeCustomerId" + consultOrder.getCustomer().getEntityId());
        templateMessage.setToUser(seller.getOpenId());

        List<WxMpTemplateData> templateDataList = new ArrayList<>();

        String firstData = messageSource.getMessage("push.CatchConsultOrderToSeller.first",
                new Object[]{seller.getRealName()}, Locale.CHINA);
        String remarkData = messageSource.getMessage("push.CatchConsultOrderToSeller.remark",
                new Object[]{}, Locale.CHINA);

        templateDataList.add(new WxMpTemplateData("first", firstData));
        templateDataList.add(new WxMpTemplateData("keyword1", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new Date(relationEntity.getCreateTime().getTime()))));
        templateDataList.add(new WxMpTemplateData("keyword2", consultOrder.getCity().getRegionName()));
        templateDataList.add(new WxMpTemplateData("keyword3", consultOrder.getCustomer().getRealName()));
        templateDataList.add(new WxMpTemplateData("remark", remarkData));

        templateMessage.setDatas(templateDataList);
        try {
            pushMessage(templateMessage);
        } catch (WxErrorException e) {
            log.error("推送失败：", e);
        }
    }
}
