package cn.glor.xiaolun.app.repository.impl;

import cn.glor.xiaolun.app.entity.WxTemplateMessageEntity;
import cn.glor.xiaolun.app.repository.WxTemplateMessageInfoRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by caosh on 2015/10/30.
 */
@Repository
public class WxTemplateMessageInfoRepositoryImpl extends AbstractEntityRepository implements WxTemplateMessageInfoRepository {

    @Autowired
    public WxTemplateMessageInfoRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public WxTemplateMessageEntity findByName(String name) {
        return (WxTemplateMessageEntity) getSession().createQuery("from WxTemplateMessageEntity t where entityStatus = '1' and t.tplName = ?")
                .setParameter(0, name)
                .setCacheable(true)
                .uniqueResult();
    }
}
