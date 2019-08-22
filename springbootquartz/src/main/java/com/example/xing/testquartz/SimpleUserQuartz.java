package com.example.xing.testquartz;

import com.example.xing.testquartz.job.SimpleJob;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author xiexingxing
 * @Created by 2019-08-18 18:04.
 */
public class SimpleUserQuartz {
    public static void main(String[] args) throws SchedulerException {
        //1. 创建Scheduler工厂
        StdSchedulerFactory factory = new StdSchedulerFactory();

        //2. 从工厂获取调度器实例
        Scheduler scheduler = factory.getScheduler();

        //3.创建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withDescription(" this is a simple Job")
                .withIdentity("simpleJob", "Test")
                .build();


        Long startTime = System.currentTimeMillis() + 3 * 1000L;

        //4. 创建Trigger
        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .startAt(new Date(startTime)) //可设置执行时间
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()      //简单调度表。按固定时间来
                                .withRepeatCount(2)                 //重复执行
                                .withIntervalInMilliseconds(3000)   //每10毫秒执行一次
                )
                .build();

        //5.注册任务和定时器
        scheduler.scheduleJob(jobDetail, trigger);

        //6.启动 调度器
        scheduler.start();
    }
}
