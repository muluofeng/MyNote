package com.example.xing.service;

import com.example.xing.dao.SysUserDao;
import com.example.xing.entity.SysUser;
import com.example.xing.form.ChangePassForm;
import com.example.xing.form.SysUserForm;

/**
 * 系统用户
 *
 * @author chenxing
 * @email mail@chenxing.vip
 * @date 2019-01-01 01:57:06
 */
public interface SysUserService extends BaseService<SysUser, Long, SysUserDao> {

    /**
     * 保存或者更新管理员信息
     *
     * @param form
     * @return
     */
    SysUser save(SysUserForm form) throws Exception;

    /**
     * 修改密码
     *
     * @param form
     */
    void updatePassword(ChangePassForm form);
}

