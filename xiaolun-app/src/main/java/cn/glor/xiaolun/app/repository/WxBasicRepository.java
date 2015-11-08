package cn.glor.xiaolun.app.repository;

import cn.glor.xiaolun.app.entity.WxMenuEntity;

import java.util.List;

/**
 * Created by heylear on 15/10/31.
 */
public interface WxBasicRepository extends BaseRepository {

    List<WxMenuEntity> getWxMenuEntityList();


}
