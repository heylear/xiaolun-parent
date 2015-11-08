package cn.glor.xiaolun.app.service;

import cn.glor.xiaolun.app.common.exception.BusinessException;
import cn.glor.xiaolun.app.entity.ConsultOrderEntity;

/**
 * Created by caosh on 2015/10/30.
 */
public interface ConsultOrderService {

    /**
     * 客户下顾问单
     */
    void create(ConsultOrderEntity consultOrder);

    /**
     * 分发顾问单
     */
    void dispatchOrderToCatchableSellers(Object consultId);

    /**
     * 顾问抢单
     */
    void catchBySeller(long sellerId, long consultId) throws BusinessException;
}