package cn.glor.xiaolun.app.repository;

import cn.glor.xiaolun.app.entity.RegionEntity;

import java.util.List;

/**
 * Created by caosh on 2015/10/24.
 */
public interface RegionRepository {

    List<RegionEntity> allOpened();
}
