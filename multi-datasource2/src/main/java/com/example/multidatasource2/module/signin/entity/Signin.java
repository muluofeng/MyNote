package com.example.multidatasource2.module.signin.entity;

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
 * 签到记录表
 *
 * @author chenxing
 * @email mail@chenxing.vip
 * @date 2019-01-01 02:13:25
 */
@Getter
@Setter
@Entity
@Table(name = "signin")
public class Signin implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 用户id
     */
    @Column(name = "userid")
    private Integer userid;
    /**
     * 签到时间
     */
    @Column(name = "ctime")
    private Date ctime;
    /**
     * 签到当前天：比如20150907
     */
    @Column(name = "date")
    private String date;


}
