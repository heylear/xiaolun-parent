package cn.glor.xiaolun.app.repository.impl;

import cn.glor.xiaolun.app.entity.LocationEntity;
import cn.glor.xiaolun.app.entity.MpUserEntity;
import cn.glor.xiaolun.app.repository.LocationRepository;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by heylear on 2015/10/24.
 */
@Repository
public class LocationRepositoryImpl extends AbstractEntityRepository implements LocationRepository {

    @Autowired
    public LocationRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected MpUserEntity getMpUserByOpenId(String openId) {
        return queryUniqueEntity(MpUserEntity.class,Restrictions.eq("openId", openId));
    }

    @Override
    public LocationEntity getLocationEntityByUserId(long userId) {
        return queryUniqueEntity(LocationEntity.class, Restrictions.eq("memUserId",userId));
    }

    @Override
    public void saveOrUpdateLoction(String openId, double latitude, double longitude) {
        MpUserEntity mpUserEntity = getMpUserByOpenId(openId);

        if(mpUserEntity != null) {
            LocationEntity locationEntity = getLocationEntityByUserId(mpUserEntity.getEntityId());
            if(locationEntity != null){
                locationEntity.setPreLocX(locationEntity.getCurLocX());
                locationEntity.setPreLocY(locationEntity.getCurLocY());
            }else {
                locationEntity = new LocationEntity();
                locationEntity.setMemUserId(mpUserEntity.getEntityId());
            }
            locationEntity.setCurLocX(latitude);
            locationEntity.setCurLocY(longitude);
            super.saveOrUpdate(locationEntity);
        }
    }
}
