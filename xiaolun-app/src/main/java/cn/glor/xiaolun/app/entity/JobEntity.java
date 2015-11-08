package cn.glor.xiaolun.app.entity;

import org.quartz.JobKey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by caosh on 2015/11/3.
 */
@Entity
@Table(name = "tt_job", schema = "", catalog = "xlap")
public class JobEntity extends BaseEntity {

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "job_group")
    private String jobGroup;

    @Column(name = "bean_name")
    private String beanName;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "cron_expr")
    private String cronExpr;

    @Column(name = "enabled")
    private Boolean enabled;

    public static JobEntity justInvoking(String jobName, String beanName, String methodName) {
        JobEntity jobEntity = new JobEntity();
        jobEntity.jobName = jobName;
        jobEntity.beanName = beanName;
        jobEntity.methodName = methodName;
        return jobEntity;
    }

    @Transient
    public JobKey getJobKey() {
        return JobKey.jobKey(jobName, jobGroup);
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getCronExpr() {
        return cronExpr;
    }

    public void setCronExpr(String cronExpr) {
        this.cronExpr = cronExpr;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "JobEntity{" +
                "jobName='" + jobName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", beanName='" + beanName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", cronExpr='" + cronExpr + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
