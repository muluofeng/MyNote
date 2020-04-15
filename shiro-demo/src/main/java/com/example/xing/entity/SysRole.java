package com.example.xing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 角色
 *
 * @author chenxing
 * @email mail@chenxing.vip
 * @date 2019-01-01 02:13:23
 */
@Getter
@Setter
@Entity
@Table(name = "tb_sys_role")
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 部门ID
     */
    @Column(name = "dept_id")
    private Long deptId;
    /**
     * 创建时间
     */
    @Column(name = "ctime")
    private Date ctime;


}
