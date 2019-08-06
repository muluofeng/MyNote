package com.example.xing.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @program: town
 * @description: 修改密码form
 * @author: chenxing
 * @create: 2019-04-16 17:50
 **/
@Data
public class ChangePassForm {

    /**
     * 旧密码
     */
    @NotEmpty(message = "旧密码不能为空")
    String oldPass;

    /**
     * 新密码
     */
    @NotEmpty(message = "新密码不能为空")
    @Size(min = 6, message = "密码必须大于6位")
    String newPass;

    /**
     * 重复密码
     */
    @NotEmpty(message = "重复密码不能为空")
    String rePass;

    /**
     * 修改人id,只有超级管理员才需要传递这个参数
     */
    Long userId;
}
