package cn.glor.xiaolun.app.service.impl;

import cn.glor.xiaolun.app.entity.JobEntity;
import cn.glor.xiaolun.app.repository.JobRepository;
import cn.glor.xiaolun.app.scheduler.ServiceMethodInvokingJob;
import cn.glor.xiaolun.app.scheduler.ServiceMethodInvokingJobWithParameter;
import cn.glor.xiaolun.app.service.JobService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caosh on 2015/11/3.
 */
@Service
public class JobServiceImpl implements JobService {

    private static Log log = LogFactory.getLog(JobServiceImpl.class);

    private JobRepository jobRepository;

    private Scheduler scheduler;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, Scheduler scheduler) {
        this.jobRepository = jobRepository;
        this.scheduler = scheduler;

        List<JobEntity> allJobs = jobRepository.getAll();
        for (JobEntity jobEntity : allJobs) {
            try {
                if (!scheduler.checkExists(jobEntity.getJobKey())) {
                    createJobInQuartz(jobEntity);
                }
            } catch (SchedulerException e) {
                log.error("初始化任务失败", e);
            }
        }

    }

    @Override
    public void create(JobEntity jobEntity) {
        jobRepository.saveOrUpdate(jobEntity);

        createJobInQuartz(jobEntity);
    }

    @Override
    public void update(JobEntity jobEntity) {
        deleteJobInQuartz(jobEntity);

        jobRepository.saveOrUpdate(jobEntity);

        createJobInQuartz(jobEntity);
    }

    @Override
    public void delete(JobEntity jobEntity) {
        deleteJobInQuartz(jobEntity);

        jobRepository.deleteEntity(jobEntity);
    }

    @Override
    public List<JobEntity> getAll() {
        return jobRepository.getAll();
    }

    @Override
    public void createImmediately(JobEntity jobEntity, Object parameter) {
        JobDetail jobDetail = JobBuilder.newJob(ServiceMethodInvokingJobWithParameter.class).withIdentity(jobEntity.getJobKey())
                .usingJobData("beanName", jobEntity.getBeanName())
                .usingJobData("methodName", jobEntity.getMethodName())
                .build();
        jobDetail.getJobDataMap().put("parameter", parameter);
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(SimpleScheduleBuilder.simpleSchedule()).build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("创建即时任务：" + jobEntity.toString() + ", parameter=" + parameter);
        } catch (SchedulerException e) {
            log.error("创建即时任务失败", e);
        }
    }

    private void createJobInQuartz(JobEntity jobEntity) {
        JobDetail jobDetail = JobBuilder.newJob(ServiceMethodInvokingJob.class).withIdentity(jobEntity.getJobKey())
                .usingJobData("beanName", jobEntity.getBeanName())
                .usingJobData("methodName", jobEntity.getMethodName())
                .build();
        CronTrigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(jobEntity.getCronExpr())).build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("创建定时任务：" + jobEntity.toString());
        } catch (SchedulerException e) {
            log.error("创建定时任务失败", e);
        }
    }

    private void deleteJobInQuartz(JobEntity jobEntity) {
        try {
            scheduler.deleteJob(jobEntity.getJobKey());
            log.info("删除定时任务：" + jobEntity.toString());
        } catch (SchedulerException e) {
            log.error("删除定时任务失败", e);
        }
    }
}
