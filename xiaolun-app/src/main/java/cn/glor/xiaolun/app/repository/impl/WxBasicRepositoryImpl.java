package cn.glor.xiaolun.app.repository.impl;

import cn.glor.xiaolun.app.entity.WxMenuEntity;
import cn.glor.xiaolun.app.repository.QuestionRepository;
import cn.glor.xiaolun.app.repository.WxBasicRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by heylear on 2015/10/24.
 */
@Repository
public class WxBasicRepositoryImpl extends AbstractEntityRepository implements WxBasicRepository {

    @Autowired
    public WxBasicRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    @Override
    public List<WxMenuEntity> getWxMenuEntityList() {
        return queryEntityList(WxMenuEntity.class);
    }
}
