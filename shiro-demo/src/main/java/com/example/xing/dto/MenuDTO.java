package com.example.xing.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xiexingxing
 * @Created by 2019-02-14 4:09 PM.
 */
@Getter
@Setter
@ApiModel(value = "菜单列表对象")
public class MenuDTO implements Serializable {

    @ApiModelProperty(value = "路径", name = "path")
    private String path;
    @ApiModelProperty(value = "名字", name = "name")
    private String name;
    @ApiModelProperty(value = "组件", name = "component")
    private String component;
    @ApiModelProperty(value = "目标对象", name = "meta")
    private Meta meta;
    @JsonIgnoreProperties(value = {"children"})
    private List<MenuDTO> children;

    @Getter
    @Setter
    public static class Meta {
        private String icon;
        private String title;
        private boolean hideInMenu;
    }
}
