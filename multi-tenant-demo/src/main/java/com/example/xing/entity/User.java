package com.example.xing.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xiexingxing
 * @Created by 2018-12-07 6:55 PM.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "auth_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String  username;
    private String  password;
    private Boolean locked;
    private String  salt;
    @Column(name = "lastpasswordresetdate")
    private Date    lastPasswordResetDate;

}
