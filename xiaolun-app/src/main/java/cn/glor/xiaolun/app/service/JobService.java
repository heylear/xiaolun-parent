package cn.glor.xiaolun.app.service;

import cn.glor.xiaolun.app.entity.JobEntity;

import java.util.List;

/**
 * Created by caosh on 2015/11/3.
 */
public interface JobService {

    void create(JobEntity jobEntity);

    void update(JobEntity jobEntity);

    void delete(JobEntity jobEntity);

    List<JobEntity> getAll();

    /**
     * 创建任务，立即执行
     */
    void createImmediately(JobEntity jobEntity, Object parameter);
}
