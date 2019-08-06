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
 * 用户与角色对应关系
 *
 * @author chenxing
 * @email mail@chenxing.vip
 * @date 2019-01-01 02:13:23
 */
@Getter
@Setter
@Entity
@Table(name = "tb_sys_user_role")
public class SysUserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 用户ID
	 */
	@Column(name = "user_id")
	private Long userId;
	/**
	 * 角色ID
	 */
	@Column(name = "role_id")
	private Long roleId;


}
