package com.example.getwaymanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.getwaymanager.entity.SysRouteConf;

import java.util.List;

import cn.hutool.json.JSONArray;
import reactor.core.publisher.Mono;

/**
 * 路由
 *
 * @author lengleng
 * @date 2018-11-06 10:17:18
 */
public interface SysRouteConfService extends IService<SysRouteConf> {

	/**
	 * 获取全部路由
	 * <p>
	 * RedisRouteDefinitionWriter.java
	 * PropertiesRouteDefinitionLocator.java
	 *
	 * @return
	 */
	List<SysRouteConf> routes();

	/**
	 * 更新路由信息
	 *
	 * @param routes 路由信息
	 * @return
	 */
	Mono<Void> updateRoutes(JSONArray routes);
}

