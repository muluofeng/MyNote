package com.example.xing.job.controller;

import com.example.xing.job.entity.ScheduleJobEntity;
import com.example.xing.job.service.ScheduleJobService;
import com.example.xing.job.utils.PageUtils;
import com.example.xing.job.utils.R;
import com.example.xing.job.utils.ValidatorUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 定时任务
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {
	@Autowired
	private ScheduleJobService scheduleJobService;

	/**
	 * 定时任务列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = scheduleJobService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 定时任务信息
	 */
	@RequestMapping("/info/{jobId}")
	public R info(@PathVariable("jobId") Long jobId) {
		ScheduleJobEntity schedule = scheduleJobService.selectById(jobId);

		return R.ok().put("schedule", schedule);
	}

	/**
	 * 保存定时任务
	 */
	@RequestMapping("/save")
	public R save(@RequestBody ScheduleJobEntity scheduleJob) {
		ValidatorUtils.validateEntity(scheduleJob);

		scheduleJobService.save(scheduleJob);

		return R.ok();
	}

	/**
	 * 修改定时任务
	 */
	@RequestMapping("/update")
	public R update(@RequestBody ScheduleJobEntity scheduleJob) {
		ValidatorUtils.validateEntity(scheduleJob);

		scheduleJobService.update(scheduleJob);

		return R.ok();
	}

	/**
	 * 删除定时任务
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] jobIds) {
		scheduleJobService.deleteBatch(jobIds);

		return R.ok();
	}

	/**
	 * 立即执行任务
	 */
	@RequestMapping("/run")
	public R run(@RequestBody Long[] jobIds) {
		scheduleJobService.run(jobIds);

		return R.ok();
	}

	/**
	 * 暂停定时任务
	 */
	@RequestMapping("/pause")
	public R pause(@RequestBody Long[] jobIds) {
		scheduleJobService.pause(jobIds);

		return R.ok();
	}

	/**
	 * 恢复定时任务
	 */
	@RequestMapping("/resume")
	public R resume(@RequestBody Long[] jobIds) {
		scheduleJobService.resume(jobIds);

		return R.ok();
	}

}
