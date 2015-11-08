package cn.glor.xiaolun.app.repository;

/**
 * Created by heylear on 15/10/31.
 */
public interface CarPicRepository extends BaseRepository {
    void deleteCarPicByAttachId(long attachId);
}
