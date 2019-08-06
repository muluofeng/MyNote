package com.example.xing.form;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: town
 * @description: 系统用户修改信息
 * @author: chenxing
 * @create: 2019-04-17 15:01
 **/
@Getter
@Setter
public class SysUserUpdaterForm implements Serializable {

    private String email;

    private String mobile;

    private String nickname;

}
