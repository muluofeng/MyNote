package com.example.xing.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 菜单管理
 *
 * @author chenxing
 * @email mail@chenxing.vip
 * @date 2019-02-14 16:46:22
 */
@Getter
@Setter
@Entity
@Table(name = "tb_sys_menu")
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;
    /**
     * 父菜单ID，一级菜单为0
     */
    @Column(name = "parent_id")
    private Long parentId;
    /**
     * 菜单标题
     */
    @Column(name = "title")
    private String title;
    /**
     * 菜单URL
     */
    @Column(name = "url")
    private String url;
    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    @Column(name = "perms")
    private String perms;
    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    @Column(name = "type")
    private Integer type;
    /**
     * 菜单图标
     */
    @Column(name = "icon")
    private String icon;
    /**
     * 排序
     */
    @Column(name = "order_num")
    private Integer orderNum;
    /**
     * 是否隐藏,0不隐藏，1 隐藏
     */
    @Column(name = "hide_menu")
    private Integer hideMenu;
    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 菜单path
     */
    @Column(name = "path")
    private String path;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


}
