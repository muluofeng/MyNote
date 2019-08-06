package com.example.xing.dto;

import java.util.Date;

import lombok.Data;

/**
 * @author xiexingxing
 * @Created by 2019-07-31 18:22.
 */
@Data
public class SysUserDTO {

    private Long userId;

    private String username;

    private String nickname;

    private String email;

    private String mobile;

    private Integer status;

    private Long deptId;

    private Date ctime;

    private Date utime;

    private String roleIds;
}
