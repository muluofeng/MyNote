package com.example.xing.service.impl;


import com.example.xing.common.Md5DigestUtils;
import com.example.xing.common.RRException;
import com.example.xing.dao.SysUserDao;
import com.example.xing.dao.SysUserRoleDao;
import com.example.xing.dto.mapper.SysUserMapper;
import com.example.xing.entity.QSysUser;
import com.example.xing.entity.QSysUserRole;
import com.example.xing.entity.SysUser;
import com.example.xing.entity.SysUserRole;
import com.example.xing.form.ChangePassForm;
import com.example.xing.form.SysUserForm;
import com.example.xing.service.SysUserService;
import com.example.xing.shiro.ShiroUtils;
import com.querydsl.core.types.Predicate;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Long, SysUserDao> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    /**
     * 保存或者更新管理员信息
     *
     * @param form
     * @return
     */
    @Override
    public SysUser save(SysUserForm form) throws Exception {

        Assert.notNull(form, "管理员信息不能为空");

        Date date = new Date();

        SysUser updateRecord;

        String roleIds = form.getRoleIds(); // 一个用户多个角色

        if (StringUtils.isEmpty(roleIds)) {
            throw new RRException("角色不能为空");
        }

        if (null != form.getUserId()) {
            //更新

            SysUser sysUser = this.findOne(form.getUserId());

            sysUserMapper.copyTo(form, sysUser);

            sysUser.setUtime(date);

            updateRecord = this.save(sysUser);

        } else {
            //新增,判断账号是否重复
            if (this.count(QSysUser.sysUser.username.eq(form.getUsername())) > 0) {
                throw new RRException("用户名不能重复");
            }
            // 验证密码是否为空, 验证角色是否为空
            if (StringUtils.isEmpty(form.getPassword()) || !(form.getPassword()
                    .length() >= 6 && form.getPassword().length() <= 20)) {
                throw new RRException("密码必须为6-20位");
            }

            SysUser sysUser = sysUserMapper.from(form);

            String salt = Md5DigestUtils.generatorSalt();

            String pwdStr = ShiroUtils.sha256(form.getPassword(), salt);

            //生成20位盐
            sysUser.setSalt(salt);
            sysUser.setPassword(pwdStr);

            sysUser.setCtime(date);
            sysUser.setUtime(date);

            updateRecord = this.save(sysUser);

        }

        //更新用户角色
        Long userId = updateRecord.getUserId();

        // 清除过期的角色
        Predicate userRolePre = QSysUserRole.sysUserRole.userId.eq(userId);
        Iterable<SysUserRole> roles = sysUserRoleDao.findAll(userRolePre);
        sysUserRoleDao.deleteInBatch(roles);

        //转换成角色用户信息表
        if (StringUtils.isNotBlank(roleIds)) {
            List<SysUserRole> updateUserRoles = Arrays.stream(roleIds.split(",")).map(roleId -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(Long.parseLong(roleId));
                return userRole;
            }).collect(Collectors.toList());

            sysUserRoleDao.saveAll(updateUserRoles);
        }

        return updateRecord;

    }

    /**
     * 修改密码
     *
     * @param form
     */
    @Override
    public void updatePassword(ChangePassForm form) {

        //修改密码
        Assert.notNull(form, "信息不能为空");

        SysUser sysUser = findOne(form.getUserId());

        Assert.notNull(sysUser, "查找用户失败");

        //验证老密码
        if (!sysUser.getPassword().equals(ShiroUtils.sha256(form.getOldPass(), sysUser.getSalt()))) {
            throw new RRException("验证原密码失败");
        }

        String salt = Md5DigestUtils.generatorSalt();

        String pwdStr = ShiroUtils.sha256(form.getNewPass(), salt);

        //生成盐
        sysUser.setSalt(salt);
        sysUser.setPassword(pwdStr);

        sysUser.setCtime(new Date());

        this.save(sysUser);

    }

}
