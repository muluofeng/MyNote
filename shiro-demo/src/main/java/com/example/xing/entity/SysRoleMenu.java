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
 * 角色与菜单对应关系
 *
 * @author chenxing
 * @email mail@chenxing.vip
 * @date 2019-01-01 02:13:23
 */
@Getter
@Setter
@Entity
@Table(name = "tb_sys_role_menu")
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;
    /**
     * 菜单ID
     */
    @Column(name = "menu_id")
    private Long menuId;


}
