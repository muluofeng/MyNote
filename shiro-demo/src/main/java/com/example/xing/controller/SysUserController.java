package com.example.xing.controller;

import com.example.xing.common.PageableRequest;
import com.example.xing.common.R;
import com.example.xing.common.SysConstant;
import com.example.xing.dao.SysUserRoleService;
import com.example.xing.dto.SysUserDTO;
import com.example.xing.dto.mapper.SysUserMapper;
import com.example.xing.entity.QSysUser;
import com.example.xing.entity.QSysUserRole;
import com.example.xing.entity.SysUser;
import com.example.xing.entity.SysUserRole;
import com.example.xing.form.ChangePassForm;
import com.example.xing.form.CommonForm;
import com.example.xing.form.SysUserForm;
import com.example.xing.form.SysUserUpdaterForm;
import com.example.xing.service.SysUserService;
import com.example.xing.shiro.ShiroUtils;
import com.querydsl.core.BooleanBuilder;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

/**
 * 系统用户
 *
 * @author chenxing
 * @email mail@chenxing.vip
 * @date 2019-01-10 12:17:55
 */
@RestController
@RequestMapping("admin/sys/user")
public class SysUserController {

    private final static String SEARCH_BY_NAME = "0";
    private final static String SEARCH_BY_EMAIL = "1";
    private final static String SEARCH_BY_MOBILE = "2";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("sys:user:list")
    public R list(String searchType, String searchKey, CommonForm form) {

        QSysUser sysUser = QSysUser.sysUser;

        // 根据搜索类型, 对关键字进行模糊查询

        //忽略超级管理员的账号, 超级管理员账号不能出现在列表中
        BooleanBuilder builder = new BooleanBuilder(sysUser.userId.ne(SysConstant.SUPER_ADMIN_ID));

        if (StringUtils.isNotEmpty(searchKey)) {
            if (SEARCH_BY_NAME.equals(searchType)) {
                builder.and(sysUser.username.like("%" + searchKey + "%"));
            } else if (SEARCH_BY_EMAIL.equals(searchType)) {
                builder.and(sysUser.email.like("%" + searchKey + "%"));

            } else if (SEARCH_BY_MOBILE.equals(searchType)) {
                builder.and(sysUser.mobile.like("%" + searchKey + "%"));
            }
        }

        //从表单中获得分页信息和排序信息
        PageRequest pageRequest = PageableRequest.of(form);

        //查询分页
        Page<SysUser> pageResult = sysUserService.findPage(builder, pageRequest);

        return R.ok().put("page", pageResult);
    }

    /**
     * 获得当前登录用户信息
     */
    @RequestMapping("/userInfo")
//    @RequiresPermissions("sys:user:info")
    public R userInfo() {

        SysUserDTO userDTO = sysUserMapper.toDTO(ShiroUtils.getUserEntity());
        return R.ok().put("sysUser", userDTO);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
//    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId) {
        SysUser tbSysUser = sysUserService.findOne(userId);

        Assert.notNull(tbSysUser, "用户不存在");
        SysUserDTO userDTO = sysUserMapper.toDTO(tbSysUser);

        // 获取角色
        List<SysUserRole> userRoles = sysUserRoleService.findAll(QSysUserRole.sysUserRole.userId.eq(userId));

        StringBuffer userIds = new StringBuffer();
        if (null != userRoles && !userRoles.isEmpty()) {
            userRoles.stream().map(SysUserRole::getRoleId).forEach(r -> {
                userIds.append(",").append(r);
            });

            userDTO.setRoleIds(userIds.substring(1));
        }

        return R.ok().put("sysUser", userDTO);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("sys:user:save")
    public R save(@Valid SysUserForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return R.error(bindingResult.getAllErrors().toString());
        }
        try {

            if (!SysConstant.SUPER_ADMIN_ID.equals(ShiroUtils.getUserId())) {
                //如果不是超级管理员, 没有权限更改其他用户的信息
                return R.error("没有权限修改,请联系超级管理员");
            }

            sysUserService.save(form);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.toString());
        }
    }

    /**
     * 修改密码
     */
    @PostMapping("/changePass")
    public R changPwd(@Valid ChangePassForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return R.error(bindingResult);
        }
        try {
            if (!form.getNewPass().equals(form.getRePass())) {
                return R.error("新密码和重复密码不一致");
            }

            if (null != form.getUserId()) {
                //如果不为空, 则验证是否是超级管理员身份
                if (!SysConstant.SUPER_ADMIN_ID.equals(ShiroUtils.getUserId())) {
                    //如果不是超级管理员, 没有权限更改其他用户的密码
                    return R.error("没有权限修改,请联系超级管理员");
                }
            } else {
                //如果为空,则使用当前登录用户id 作为修改对象
                form.setUserId(ShiroUtils.getUserId());
            }

            //验证管理员身份
            sysUserService.updatePassword(form);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.toString());
        }

    }

    /**
     * 修改自己的信息
     */
    @RequestMapping("/update")
//    @RequiresPermissions("sys:user:update")
    public R update(SysUserUpdaterForm form) {

        SysUser userEntity = ShiroUtils.getUserEntity();

        userEntity.setNickname(form.getNickname());
        userEntity.setEmail(form.getEmail());
        userEntity.setMobile(form.getMobile());

        sysUserService.save(userEntity);//更新自己的信息

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("sys:user:delete")
    public R delete(Long[] ids) {
        sysUserService.delete(ids);

        return R.ok();
    }

}
