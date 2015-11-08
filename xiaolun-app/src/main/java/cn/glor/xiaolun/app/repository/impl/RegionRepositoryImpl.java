package cn.glor.xiaolun.app.repository.impl;

import cn.glor.xiaolun.app.entity.RegionEntity;
import cn.glor.xiaolun.app.repository.RegionRepository;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by caosh on 2015/10/24.
 */
@Repository
public class RegionRepositoryImpl extends AbstractEntityRepository implements RegionRepository {

    @Autowired
    public RegionRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<RegionEntity> allOpened() {
        return getSession().createCriteria(RegionEntity.class)
                .add(Restrictions.eq("bizOpened", 1))
                .setCacheable(true).list();
    }
}
