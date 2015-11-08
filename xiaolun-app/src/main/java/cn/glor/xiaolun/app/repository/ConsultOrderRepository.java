package cn.glor.xiaolun.app.repository;

import cn.glor.xiaolun.app.common.page.PageQuery;
import cn.glor.xiaolun.app.common.page.PageResult;
import cn.glor.xiaolun.app.entity.ConsultOrderEntity;
import cn.glor.xiaolun.app.entity.ConsultOrderSellerRelation;
import cn.glor.xiaolun.app.entity.MpUserEntity;
import cn.glor.xiaolun.app.entity.SellerAccountEntity;

import java.util.List;

/**
 * Created by caosh on 2015/10/8.
 */
public interface ConsultOrderRepository {

    Long save(ConsultOrderEntity consultOrder);

    List<ConsultOrderEntity> findByCustomer(long customerId);

    List<ConsultOrderEntity> findBySeller(long customerId);

    ConsultOrderEntity get(long consultId);

    List<ConsultOrderEntity> findCatchableOrderListBySeller(SellerAccountEntity seller);

    PageResult<SellerAccountEntity> findCatchableSellerListByConsult(ConsultOrderEntity consult, PageQuery pageQuery);

    Long saveSellerRelation(ConsultOrderSellerRelation relationEntity);

    ConsultOrderSellerRelation getSellerRelation(long relationId);

    ConsultOrderSellerRelation findRelationBySellerCustomer(Long sellerId, Long customerId);

    List<SellerAccountEntity> findSellerListByCustomer(long customerId);

    List<MpUserEntity> findCustomerListBySeller(long sellerId);
}
