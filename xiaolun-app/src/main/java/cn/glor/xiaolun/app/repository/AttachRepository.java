package cn.glor.xiaolun.app.repository;

import cn.glor.xiaolun.app.entity.AttachEntity;

import java.util.List;

/**
 * Created by heylear on 15/10/31.
 */
public interface AttachRepository extends BaseRepository {
    List<AttachEntity> getAttachEntityListByUserId(long userId);
    AttachEntity updateOrLoadAttachEntityById(long attachId);
}
