package cn.glor.xiaolun.app.repository.impl;

import cn.glor.xiaolun.app.entity.CarPicEntity;
import cn.glor.xiaolun.app.repository.CarPicRepository;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by heylear on 2015/10/24.
 */
@Repository
public class CarPicRepositoryImpl extends AbstractEntityRepository implements CarPicRepository {

    @Autowired
    public CarPicRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void deleteCarPicByAttachId(long attachId) {
        CarPicEntity carPicEntity = queryUniqueEntity(CarPicEntity.class, Restrictions.eq("attachId", attachId));
        if (carPicEntity != null) {
            super.deleteEntity(carPicEntity);
        }
    }

}
