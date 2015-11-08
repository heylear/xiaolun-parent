package cn.glor.xiaolun.app.repository;

import cn.glor.xiaolun.app.entity.LocationEntity;

/**
 * Created by heylear on 15/10/31.
 */
public interface LocationRepository extends BaseRepository {
    LocationEntity getLocationEntityByUserId(long userId);
    void saveOrUpdateLoction(String openId, double latitude, double longitude);
}
