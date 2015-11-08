package cn.glor.xiaolun.app.repository;

import cn.glor.xiaolun.app.entity.ConfigEntity;

/**
 * Created by caosh on 2015/10/30.
 */
public interface ConfigRepository extends BaseRepository {

    ConfigEntity getByKey(String key);

    String getValue(String key);

}
