package cn.glor.xiaolun.app.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

/**
 * 约定：调用目标Bean的无参方法，等于beanName.methodName();
 *
 * Created by caosh on 2015/11/3.
 */
public class ServiceMethodInvokingJob implements Job {

    private Log log = LogFactory.getLog(ServiceMethodInvokingJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            ApplicationContext applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContextKey");
            String beanName = context.getJobDetail().getJobDataMap().getString("beanName");
            String methodName = context.getJobDetail().getJobDataMap().getString("methodName");
            Object bean = applicationContext.getBean(beanName);
            ReflectionUtils.findMethod(bean.getClass(), methodName).invoke(bean);
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
