package com.example.xing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
 * 系统用户
 *
 * @author chenxing
 * @email mail@chenxing.vip
 * @date 2019-01-01 02:13:23
 */
@Getter
@Setter
@Entity
@Table(name = "tb_sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 姓名
     */
    @Column(name = "nickname")
    private String nickname;


    /**
     * 密码
     */
    @JsonIgnore
    @Column(name = "password")
    private String password;
    /**
     * 盐
     */
    @JsonIgnore
    @Column(name = "salt")
    private String salt;
    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;
    /**
     * 手机号
     */
    @Column(name = "mobile")
    private String mobile;
    /**
     * 状态  0：禁用   1：正常
     */
    @Column(name = "status")
    private Integer status;
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

    /**
     * 最后更新时间
     */
    @Column(name = "utime")
    private Date utime;

}
