package com.example.xing.job.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.xing.job.dao.ScheduleJobLogDao;
import com.example.xing.job.entity.ScheduleJobLogEntity;
import com.example.xing.job.service.ScheduleJobLogService;
import com.example.xing.job.utils.PageUtils;
import com.example.xing.job.utils.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String jobId = (String) params.get("jobId");

		Page<ScheduleJobLogEntity> page = this.selectPage(new Query<ScheduleJobLogEntity>(params).getPage(), new EntityWrapper<ScheduleJobLogEntity>().like(StringUtils.isNotBlank(jobId), "job_id", jobId));

		return new PageUtils(page);
	}

}
