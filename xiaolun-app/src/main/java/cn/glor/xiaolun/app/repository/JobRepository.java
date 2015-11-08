package cn.glor.xiaolun.app.repository;

import cn.glor.xiaolun.app.entity.JobEntity;

import java.util.List;

/**
 * Created by caosh on 2015/11/3.
 */
public interface JobRepository extends BaseRepository {

    List<JobEntity> getAll();
}
