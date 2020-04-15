package com.example.xing.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 菜单管理
 * <p>
 * The Class SysMenuVO
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SysMenuVO对象", description = "SysMenuVO对象")
public class SysMenuVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "响应码", example = "0", required = true)
    private String id;

    /**
     * 菜单权限
     */
    @ApiModelProperty(value = "菜单权限", name = "perms")
    private String perms;

    /**
     * 菜单名称，菜单显示的字面值.
     */
    @ApiModelProperty(value = "菜单名字", name = "title")
    private String title;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单标志", name = "icon")
    private String icon;

    /**
     * 菜单URL.
     */
    @ApiModelProperty(value = "url", name = "url")
    private String url = "";

    /**
     * 父节点.
     */
    @ApiModelProperty(value = "父级多像", name = "parent")
    private SysMenuVO parent;

    /**
     * 父节id.
     */
    @ApiModelProperty(value = "父级Id", name = "parentid")
    private Long parentid;


    /**
     * 孩子节点.
     */
    @ApiModelProperty(value = "子级对象", name = "children")
    private List<SysMenuVO> children = new ArrayList<SysMenuVO>();

    /**
     * 是否默认展开菜单组
     */
    @ApiModelProperty(value = "子级对象", name = "children")
    private Boolean open = Boolean.FALSE;

    /**
     * 显示标识
     */
    @ApiModelProperty(value = "子级对象", name = "children")
    private Boolean show = Boolean.FALSE;

    /**
     * 菜单是否隐藏
     */
    @ApiModelProperty(value = "是否隐藏", name = "hidemenu")
    private Integer hidemenu;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序值", name = "ordernum")
    private Integer ordernum;

    /**
     * 路径
     */
    @ApiModelProperty(value = "路径", name = "path")
    private String path;
    /**
     * 英文名
     */
    @ApiModelProperty(value = "英文名", name = "name")
    private String name;


    public void setShow(Boolean show) {
        this.show = show;
        if (show == true & this.getParent() != null) {
            this.getParent().setShow(true);
        }
    }

}
