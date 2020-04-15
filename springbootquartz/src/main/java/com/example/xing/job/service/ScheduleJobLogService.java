package com.example.xing.job.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.xing.job.entity.ScheduleJobLogEntity;
import com.example.xing.job.utils.PageUtils;

import java.util.Map;

/**
 * 定时任务日志
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
