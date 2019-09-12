package com.example.multidatasource.entity;


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
 * 用户
 *
 * @author chenxing
 * @email mail@chenxing.vip
 * @date 2019-01-12 16:46:04
 */
@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 创建时间
     */
    @Column(name = "ctime")
    private Date ctime;
}
