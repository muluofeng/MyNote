package com.example.xing.form;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "保存菜单")
public class SaveMenuForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@ApiModelProperty(value = "菜单ID 修改菜单时不能为空")
	private Long menuId;
	/**
	 * 父菜单ID，一级菜单为0
	 */
	@ApiModelProperty(value = "父级ID 不能为空")
	private Long parentId;
	/**
	 * 菜单标题
	 */
	@ApiModelProperty(value = "菜单标题  不能为空")
	private String title;
	/**
	 * 菜单URL
	 */
	@ApiModelProperty(value = "url")
	private String url;
	/**
	 * 菜单图标
	 */
	@ApiModelProperty(value = "菜单图标  不能为空")
	private String icon;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序数值  不能为空")
	private Integer orderNum;
	/**
	 * 是否隐藏,0不隐藏，1 隐藏
	 */
	@ApiModelProperty(value = "设置菜单显隐  不能为空")
	private Integer hideMenu;
	/**
	 * 权限标识
	 */
	@ApiModelProperty(value = "权限标识  不能为空")
	private String perms;
	/**
	 * 路径
	 */
	@ApiModelProperty(value = "路径  不能为空")
	private String path;
	/**
	 * 英文名
	 */
	@ApiModelProperty(value = "英文名  不能为空")
	private String name;
	/**
	 * 类型
	 */	
	@ApiModelProperty(value = "类型   0：目录   1：菜单   2：按钮")
	private Integer type;
	
	
}
