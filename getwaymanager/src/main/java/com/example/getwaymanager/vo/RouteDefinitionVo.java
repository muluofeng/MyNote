
package com.example.getwaymanager.vo;


import org.springframework.cloud.gateway.route.RouteDefinition;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lengleng
 * @date 2018/10/31
 * <p>
 * 扩展此类支持序列化a
 * See RouteDefinition.class
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RouteDefinitionVo extends RouteDefinition implements Serializable {
	/**
	 * 路由名称
	 */
	private String routeName;
}
