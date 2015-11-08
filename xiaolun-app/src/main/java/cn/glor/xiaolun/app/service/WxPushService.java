package cn.glor.xiaolun.app.service;

/**
 * Created by caosh on 2015/10/26.
 */
public interface WxPushService {

    /**
     * 客户下顾问单后推送给客户，通知下单成功
     */
    void pushCreateConsultOrderToCustomer(Object consultId);

    /**
     * 客户下顾问单后推送给顾问，通知可以抢单
     */
    void pushCreateConsultOrderToSeller(Object parameter);

    /**
     * 顾问抢单成功后推送给客户，通知有人接单
     */
    void pushCatchConsultOrderToCustomer(Object relationId);

    /**
     * 顾问抢单成功后推送给顾问，通知抢单成功
     */
    void pushCatchConsultOrderToSeller(Object relationId);
}
