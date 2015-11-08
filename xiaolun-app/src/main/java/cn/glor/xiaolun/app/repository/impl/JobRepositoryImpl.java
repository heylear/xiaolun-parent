package cn.glor.xiaolun.app.repository.impl;

import cn.glor.xiaolun.app.entity.JobEntity;
import cn.glor.xiaolun.app.repository.JobRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by caosh on 2015/11/3.
 */
@Repository
public class JobRepositoryImpl extends AbstractEntityRepository implements JobRepository {

    @Autowired
    public JobRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<JobEntity> getAll() {
        return queryEntityList(JobEntity.class);
    }
}
