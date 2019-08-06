package com.example.xing.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * 管理员表单
 */
@Getter
@Setter
public class SysUserForm implements Serializable {

    private Long userId;

    @NotEmpty(message = "用户名不能为空")
    private String username;

    private String nickname;

    private String password;

    private String email;

    private String mobile;

    private Integer status;

    private Long deptId;

    //角色id
    private String roleIds; // 多个用逗号隔开


}
