package cn.glor.xiaolun.app.repository;

import cn.glor.xiaolun.app.entity.SubBrandEntity;

import java.util.List;

/**
 * Created by caosh on 2015/10/18.
 */
public interface SubBrandRepository {

    List<SubBrandEntity> searchByPinYin(String pinYin);

}
