package cn.glor.xiaolun.app.repository.impl;

import cn.glor.xiaolun.app.entity.ConfigEntity;
import cn.glor.xiaolun.app.repository.ConfigRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by caosh on 2015/10/30.
 */
@Repository
public class ConfigRepositoryImpl extends AbstractEntityRepository implements ConfigRepository {

    @Autowired
    public ConfigRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public ConfigEntity getByKey(String key) {
        return (ConfigEntity) getSession().createQuery("from ConfigEntity where entityStatus = '1' and key = ?")
                .setParameter(0, key)
                .setCacheable(true)
                .uniqueResult();
    }

    @Override
    public String getValue(String key) {
        ConfigEntity config = getByKey(key);
        return config != null ? config.getValue() : null;
    }
}
