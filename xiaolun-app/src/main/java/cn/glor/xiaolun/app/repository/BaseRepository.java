package cn.glor.xiaolun.app.repository;

import cn.glor.xiaolun.app.entity.BaseEntity;

/**
 * Created by heylear on 15/10/31.
 */
public interface BaseRepository {

    void saveOrUpdate(BaseEntity entity);

    void deleteEntity(BaseEntity entity);

}
