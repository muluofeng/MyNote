package com.example.xing.testquartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author xiexingxing
 * @Created by 2019-08-18 18:04.
 */
public class SimpleJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Object jobName = jobExecutionContext.getJobDetail().getJobDataMap().get("name");
        System.out.println("被调用 - 当前" + jobName + "--时间为" + System.currentTimeMillis());
    }
}
