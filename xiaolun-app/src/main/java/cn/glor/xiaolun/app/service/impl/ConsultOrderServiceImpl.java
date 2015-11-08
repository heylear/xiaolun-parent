package cn.glor.xiaolun.app.service.impl;

import cn.glor.xiaolun.app.common.exception.BusinessException;
import cn.glor.xiaolun.app.common.exception.BusinessExceptionFactory;
import cn.glor.xiaolun.app.common.page.PageQuery;
import cn.glor.xiaolun.app.common.page.PageResult;
import cn.glor.xiaolun.app.dto.ConsultSellerDto;
import cn.glor.xiaolun.app.entity.ConsultOrderEntity;
import cn.glor.xiaolun.app.entity.ConsultOrderSellerRelation;
import cn.glor.xiaolun.app.entity.JobEntity;
import cn.glor.xiaolun.app.entity.SellerAccountEntity;
import cn.glor.xiaolun.app.repository.ConsultOrderRepository;
import cn.glor.xiaolun.app.repository.SellerAccountRepository;
import cn.glor.xiaolun.app.service.ConsultOrderService;
import cn.glor.xiaolun.app.service.JobService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by caosh on 2015/10/30.
 */
@Service("consultOrderService")
public class ConsultOrderServiceImpl implements ConsultOrderService {

    private static Log log = LogFactory.getLog(ConsultOrderServiceImpl.class);

    @Autowired
    private ConsultOrderRepository consultOrderRepository;

    @Autowired
    private SellerAccountRepository sellerAccountRepository;

    @Autowired
    private BusinessExceptionFactory businessExceptionFactory;

    @Autowired
    private JobService jobService;

    @Override
    public void create(ConsultOrderEntity consultOrder) {
        Long consultId = consultOrderRepository.save(consultOrder);

        scheduleCreateConsultOrderPushCustomerJob(consultId);

        scheduleDispatchOrderToCatchableSellersJob(consultId);
    }

    private void scheduleDispatchOrderToCatchableSellersJob(Long consultId) {
        jobService.createImmediately(JobEntity.justInvoking("dispatchOrderToCatchableSellers" + consultId,
                "consultOrderService", "dispatchOrderToCatchableSellers"), consultId);
    }

    @Override
    public void dispatchOrderToCatchableSellers(Object consultId) {
        log.info("执行顾问单派发任务：consultId=" + consultId);

        ConsultOrderEntity consultOrder = consultOrderRepository.get((Long) consultId);
        int pageNum = 1;
        PageResult<SellerAccountEntity> catchableSellers;
        do {
            catchableSellers = consultOrderRepository.findCatchableSellerListByConsult(consultOrder, PageQuery.newPageQuery(1, pageNum));
            for (SellerAccountEntity seller : catchableSellers.getDataList()) {
                scheduleCreateConsultOrderPushSellerJob(consultOrder, seller);
            }
            pageNum++;
        } while (catchableSellers.isHasNextPage());
    }

    private void scheduleCreateConsultOrderPushCustomerJob(long consultId) {
        jobService.createImmediately(JobEntity.justInvoking("pushCreateConsultOrderToCustomer" + consultId,
                "wxPushService", "pushCreateConsultOrderToCustomer"), consultId);
    }

    private void scheduleCreateConsultOrderPushSellerJob(ConsultOrderEntity consultOrder, SellerAccountEntity seller) {
        ConsultSellerDto parameter = new ConsultSellerDto(consultOrder.getEntityId(), seller.getEntityId());
        jobService.createImmediately(JobEntity.justInvoking("pushCreateConsultOrderToSeller" + parameter.toString(),
                "wxPushService", "pushCreateConsultOrderToSeller"), parameter);
    }

    @Override
    public void catchBySeller(long sellerId, long consultId) throws BusinessException {
        ConsultOrderEntity consultOrder = consultOrderRepository.get(consultId);

        if (consultOrder.getSellerList().size() >= consultOrder.getWantedConsultants()) {
            throw businessExceptionFactory.ofKey("consultOrder.overflow");
        }

        if (consultOrderRepository.findRelationBySellerCustomer(sellerId, consultOrder.getCustomer().getEntityId()) != null) {
            throw businessExceptionFactory.ofKey("seller.customerCaught");
        }

        Long relationId = saveConsultOrderSellerRelation(sellerId, consultOrder);

        updateConsultCountThisWeek(sellerId);

        scheduleCatchConsultOrderPushCustomerJob(relationId);

        scheduleCatchConsultOrderPushSellerJob(relationId);
    }

    private void updateConsultCountThisWeek(long sellerId) {
        SellerAccountEntity seller = sellerAccountRepository.get(sellerId);
        seller.setConsultCountThisWeek(seller.getConsultCountThisWeek() + 1);
        sellerAccountRepository.saveOrUpdate(seller);
    }

    private void scheduleCatchConsultOrderPushSellerJob(Long relationId) {
        jobService.createImmediately(JobEntity.justInvoking("pushCatchConsultOrderToSeller" + relationId,
                "wxPushService", "pushCatchConsultOrderToSeller"), relationId);
    }

    private void scheduleCatchConsultOrderPushCustomerJob(Long relationId) {
        jobService.createImmediately(JobEntity.justInvoking("pushCatchConsultOrderToCustomer" + relationId,
                "wxPushService", "pushCatchConsultOrderToCustomer"), relationId);
    }

    private Long saveConsultOrderSellerRelation(long sellerId, ConsultOrderEntity consultOrder) {
        ConsultOrderSellerRelation relationEntity = new ConsultOrderSellerRelation();
        relationEntity.setConsultId(consultOrder.getEntityId());
        relationEntity.setCustomerId(consultOrder.getCustomer().getEntityId());
        relationEntity.setSellerId(sellerId);
        return consultOrderRepository.saveSellerRelation(relationEntity);
    }
}
