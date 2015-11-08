package cn.glor.xiaolun.app.repository.impl;

import cn.glor.xiaolun.app.entity.SubBrandEntity;
import cn.glor.xiaolun.app.repository.SubBrandRepository;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by caosh on 2015/10/18.
 */
@Repository
public class SubBrandRepositoryImpl extends AbstractEntityRepository implements SubBrandRepository {

    @Autowired
    public SubBrandRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<SubBrandEntity> searchByPinYin(String pinYin) {
        return getSession().createCriteria(SubBrandEntity.class)
                .add(Restrictions.eq("entityStatus", "1"))
                .add(Restrictions.or(
                        Restrictions.like("name", "%" + pinYin + "%"),
                        Restrictions.like("pinyinAbbr", "%" + pinYin + "%")))
                .setCacheable(true)
                .list();
    }
}
