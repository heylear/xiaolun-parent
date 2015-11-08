package cn.glor.xiaolun.app.repository.impl;

import cn.glor.xiaolun.app.entity.SellerAccountEntity;
import cn.glor.xiaolun.app.entity.SubBrandEntity;
import cn.glor.xiaolun.app.repository.SellerAccountRepository;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by caosh on 2015/10/10.
 */
@Repository
public class SellerAccountRepositoryImpl extends AbstractEntityRepository implements SellerAccountRepository {

    @Autowired
    public SellerAccountRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public SellerAccountEntity get(Long id) {
        return getHibernateTemplate().get(SellerAccountEntity.class, id);
    }

    @Override
    public void transformMpUserToSeller(SellerAccountEntity sellerAccount) {
        getSession().createSQLQuery("insert into tt_seller_account (" +
                "entity_id, birthday, employer, work_phone, served_brand_id, description, consult_count_this_week, region_id, audit_status) values (" +
                "?, ?, ?, ?, ?, ?, 0, ?, 1)")
                .setLong(0, sellerAccount.getEntityId())
                .setDate(1, sellerAccount.getBirthday())
                .setString(2, sellerAccount.getEmployer())
                .setString(3, sellerAccount.getWorkPhone())
                .setLong(4, sellerAccount.getServedBrand().getEntityId())
                .setString(5, sellerAccount.getDescription())
                .setLong(6, sellerAccount.getRegion().getEntityId())
                .executeUpdate();
        if (sellerAccount.getExpertBrandList() != null) {
            SQLQuery insertSql = getSession().createSQLQuery("insert into tr_seller_expert_brand (seller_id, sub_brand_id, create_time) values (" +
                    "?, ?, now())");
            for (SubBrandEntity subBrand : sellerAccount.getExpertBrandList()) {
                insertSql.setParameter(0, sellerAccount.getEntityId());
                insertSql.setParameter(1, subBrand.getEntityId());
                insertSql.executeUpdate();
            }
        }
        // 清除session以使得继承关系可以重新拉取
        getSession().clear();
    }

    @Override
    public SellerAccountEntity getByWxUnionId(String unionId) {
        List resultList = getHibernateTemplate().find(
                "select s from SellerAccountEntity s where s.unionId = ?", unionId);
        if (resultList.isEmpty()) {
            return null;
        } else {
            return (SellerAccountEntity) resultList.get(0);
        }
    }

    @Override
    public SellerAccountEntity findByPhoneNumber(String phoneNumber) {
        List result = getHibernateTemplate().find("from SellerAccountEntity s where s.phoneNumber = ?", phoneNumber);
        if (result.isEmpty()) {
            return null;
        }
        return (SellerAccountEntity) result.get(0);
    }
}
