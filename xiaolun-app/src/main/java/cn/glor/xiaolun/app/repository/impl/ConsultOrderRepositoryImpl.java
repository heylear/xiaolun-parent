package cn.glor.xiaolun.app.repository.impl;

import cn.glor.xiaolun.app.common.page.PageQuery;
import cn.glor.xiaolun.app.common.page.PageResult;
import cn.glor.xiaolun.app.entity.ConsultOrderEntity;
import cn.glor.xiaolun.app.entity.ConsultOrderSellerRelation;
import cn.glor.xiaolun.app.entity.MpUserEntity;
import cn.glor.xiaolun.app.entity.SellerAccountEntity;
import cn.glor.xiaolun.app.entity.enumtype.AuditStatus;
import cn.glor.xiaolun.app.repository.ConsultOrderRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Created by caosh on 2015/10/8.
 */
@Repository
public class ConsultOrderRepositoryImpl extends AbstractEntityRepository implements ConsultOrderRepository {

    @Autowired
    public ConsultOrderRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Long save(ConsultOrderEntity consultOrder) {
        saveOrUpdate(consultOrder);
        return consultOrder.getEntityId();
    }

    @Override
    public List<ConsultOrderEntity> findByCustomer(long customerId) {
        return getHibernateTemplate().find("from ConsultOrderEntity o where o.customer.entityId = ?", customerId);
    }

    @Override
    public List<ConsultOrderEntity> findBySeller(long sellerId) {
        return getHibernateTemplate().find("from ConsultOrderEntity o where o.entityId in (select r.consultId from ConsultOrderSellerRelation r " +
                        "where r.sellerId = ?)",
                sellerId);
    }

    @Override
    public ConsultOrderEntity get(long consultId) {
        return getHibernateTemplate().get(ConsultOrderEntity.class, consultId);
    }

    @Override
    public List<ConsultOrderEntity> findCatchableOrderListBySeller(SellerAccountEntity seller) {
        if (!AuditStatus.AUDIT_PASSED.equals(seller.getAuditStatus())) {
            return Collections.emptyList();
        }

        return getHibernateTemplate().find("from ConsultOrderEntity o " +
                        "where o.sellerList.size < o.wantedConsultants " +
                        "and ? <= 100 " +
                        "and o.city.entityId = ? " +
                        "and not exists (select r from ConsultOrderSellerRelation r where r.customerId = o.customer.entityId and r.sellerId = ?) " +
                        // NOTICE: 暂时禁用
//                        "and ? in (select br.subBrandId from ConsultOrderSubBrandRelation br where br.consultId = o.consultId) " +
                        "and ? != (select c.unionId from MpUserEntity c where c.entityId = o.customer.entityId) ",
                seller.getConsultCountThisWeek(),
                seller.getRegion().getEntityId(),
                seller.getEntityId(),
//                seller.getServedBrand().getSubBrandId(),
                seller.getUnionId());
    }

    @Override
    public PageResult<SellerAccountEntity> findCatchableSellerListByConsult(ConsultOrderEntity consult, PageQuery pageQuery) {
        Query queryCount = createQueryForCatchableSellerListByConsult(consult, "select count(*) ");
        int totalRows = ((Number) queryCount.uniqueResult()).intValue();

        Query query = createQueryForCatchableSellerListByConsult(consult, "");
        query.setFirstResult(pageQuery.getFirstResult());
        query.setMaxResults(pageQuery.getPageSize());
        return PageResult.newPageResult(pageQuery, totalRows, query.list());
    }

    private Query createQueryForCatchableSellerListByConsult(ConsultOrderEntity consult, String selection) {
        String hql = "from SellerAccountEntity s " +
                // 城市：根据客户下单指定的城市，向该城市销售顾问派单
                "where s.region.entityId = ? " +
                // 顾问必须审核通过
                "and auditStatus = 1" +
                // 一周内的抢单数量：一周内抢单数量封顶100单，超过100单，本周内暂不派单
                "and consultCountThisWeek <= 100 " +
                // 已经抢过单的顾问不再允许抢单
                "and not exists (select r from ConsultOrderSellerRelation r where r.customerId = ? and r.sellerId = s.entityId) " +
                // 品牌优先：匹配客户派单的咨询品牌和销售顾问目前的服务品牌，抢单时同品牌销售顾问优先出单
                // NOTICE: 暂时禁用此规则
//                "and s.servedBrand.id in (select br.subBrandId from ConsultOrderSubBrandRelation br where br.consultId = ?) " +
                // 不可以自发自抢
                "and s.unionId != (select c.unionId from MpUserEntity c where c.entityId = ? " +
                ")";
        Query query = getSession().createQuery(selection + hql);
        query.setParameter(0, consult.getCity().getEntityId());
        query.setParameter(1, consult.getCustomer().getEntityId());
//        query.setParameter(2, consult.getConsultId());
        query.setParameter(2, consult.getCustomer().getEntityId());
        return query;
    }

    @Override
    public Long saveSellerRelation(ConsultOrderSellerRelation relationEntity) {
        saveOrUpdate(relationEntity);
        return relationEntity.getEntityId();
    }

    @Override
    public ConsultOrderSellerRelation getSellerRelation(long relationId) {
        return getHibernateTemplate().get(ConsultOrderSellerRelation.class, relationId);
    }

    @Override
    public ConsultOrderSellerRelation findRelationBySellerCustomer(Long sellerId, Long customerId) {
        return queryUniqueEntity(ConsultOrderSellerRelation.class,
                Restrictions.and(Restrictions.eq("sellerId", sellerId), Restrictions.eq("customerId", customerId)));
    }

    @Override
    public List<SellerAccountEntity> findSellerListByCustomer(long customerId) {
        return getHibernateTemplate().find("select s from SellerAccountEntity s" +
                        " where s.entityId in (select r.sellerId from ConsultOrderSellerRelation r where r.customerId = ?)",
                customerId);
    }

    @Override
    @Deprecated
    public List<MpUserEntity> findCustomerListBySeller(long sellerId) {
        return getHibernateTemplate().find("select c from MpUserEntity c" +
                        " where c.entityId in (select r.customerId from ConsultOrderSellerRelation r where r.sellerId = ?)",
                sellerId);
    }
}
