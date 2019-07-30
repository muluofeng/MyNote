package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author xiexingxing
 * @Created by 2018-12-09 2:56 PM.
 */
@Entity
@Table(name = "tb_goods")
@Getter
@Setter
@NoArgsConstructor
public class Good implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "goods_name")
    private String goodsName;
    @Column(name = "num")
    private Integer num;
    @Column(name = "num2")
    private Integer num2;

}