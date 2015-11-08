package cn.glor.xiaolun.app.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

/**
 * Created by caosh on 2015/11/4.
 */
public class ServiceMethodInvokingJobWithParameter implements Job {

    private Log log = LogFactory.getLog(ServiceMethodInvokingJobWithParameter.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            ApplicationContext applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContextKey");
            String beanName = context.getJobDetail().getJobDataMap().getString("beanName");
            String methodName = context.getJobDetail().getJobDataMap().getString("methodName");
            Object parameter = context.getJobDetail().getJobDataMap().get("parameter");
            Object bean = applicationContext.getBean(beanName);
            ReflectionUtils.findMethod(bean.getClass(), methodName, Object.class).invoke(bean, parameter);
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
