package com.example.xing.controller;

import com.example.xing.common.PageableRequest;
import com.example.xing.common.R;
import com.example.xing.common.SysConstant;
import com.example.xing.entity.QSysRole;
import com.example.xing.entity.QSysRoleMenu;
import com.example.xing.entity.SysRole;
import com.example.xing.entity.SysRoleMenu;
import com.example.xing.form.CommonForm;
import com.example.xing.service.SysRoleMenuService;
import com.example.xing.service.SysRoleService;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 角色管理
 *
 * @author 刘在飞
 * @date 2019年3月14日 下午4:47:07
 * @Description
 */
@Api("角色管理")
@RestController
@RequestMapping("admin/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 获取角色列表
     *
     * @param form 分页信息
     * @return R
     * @author 刘在飞
     * @Description
     * @date 2019年3月14日 下午5:47:09
     * @modifitor 刘在飞
     * @reason
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation("获取角色列表")
//    @RequiresPermissions("sys:role:list")
    public R list(CommonForm form) {

        //从表单中获得分页信息和排序信息
        PageRequest pageRequest = PageableRequest.of(form);

        //忽略超级管理员角色, 此角色不显示在界面上
        BooleanExpression expression = QSysRole.sysRole.roleId.ne(SysConstant.SUPER_ROLE_ID);

        //查询分页
        Page<SysRole> pageResult = sysRoleService.findPage(expression, pageRequest);
        R result = R.ok();
        result.put("page", pageResult);
        result.put("superId", SysConstant.SUPER_ROLE_ID);//超级角色ID
        return result;
    }

    /**
     * 保存角色
     *
     * @param role
     * @return R
     * @author 刘在飞
     * @Description
     * @date 2019年3月14日 下午6:08:38
     * @modifitor 刘在飞
     * @reason
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation("保存角色")
//    @RequiresPermissions("sys:role:save")
    public R save(SysRole role) {
        try {
            Assert.notNull(role, "角色信息不能为空");
            role.setRoleId(null);
            role.setCtime(new Date());
            sysRoleService.save(role);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.toString());
        }
    }

    /**
     * 修改角色
     *
     * @param role
     * @return R
     * @author 刘在飞
     * @Description
     * @date 2019年3月14日 下午6:12:01
     * @modifitor 刘在飞
     * @reason
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation("修改角色")
//    @RequiresPermissions("sys:role:update")
    public R update(SysRole role) {
        try {
            Assert.notNull(role, "角色信息不能为空");
            Long roleId = role.getRoleId();
            if (null == roleId || roleId <= 0) {
                return R.error("角色ID不能为空");
            }

            //不允许编辑超级管理员角色
            if (roleId.equals(SysConstant.SUPER_ROLE_ID)) {
                return R.error("角色ID非法");
            }

            //判断旧的是否存在
            SysRole oldRole = sysRoleService.findOne(roleId);
            Assert.notNull(oldRole, "角色不存在");
            role.setCtime(oldRole.getCtime());
            sysRoleService.save(role);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.toString());
        }
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return R
     * @author 刘在飞
     * @Description
     * @date 2019年3月14日 下午6:14:47
     * @modifitor 刘在飞
     * @reason
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation("删除角色")
//    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestParam(value = "roleId", required = true) Long roleId) {
        try {
            if (roleId == SysConstant.SUPER_ROLE_ID) {
                return R.error("超级管理员不能删除");
            }
            SysRole role = sysRoleService.findOne(roleId);
            Assert.notNull(role, "角色不存在");
            sysRoleService.delete(roleId);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.toString());
        }
    }

    /**
     * 获取角色菜单权限列表
     *
     * @param roleId 角色ID
     * @return R
     * @author 刘在飞
     * @Description
     * @date 2019年3月14日 下午5:46:41
     * @modifitor 刘在飞
     * @reason
     */
    @RequestMapping(value = "/listMenuPrivs", method = RequestMethod.POST)
    @ApiOperation("获取角色菜单权限列表")
//    @RequiresPermissions("sys:role:listMenuPrivs")
    public R listMenuPrivs(@RequestParam(value = "roleId", required = true) Long roleId) {
        BooleanExpression expression = QSysRoleMenu.sysRoleMenu.roleId.eq(roleId);
        List<SysRoleMenu> roleMenuList = sysRoleMenuService.findAll(expression);
        return R.ok().put("roleMenuList", roleMenuList);
    }

    @RequestMapping(value = "/updateMenuPrivs", method = RequestMethod.POST)
    @ApiOperation("修改角色菜单权限")
//    @RequiresPermissions("sys:role:updateMenuPrivs")
    public R updateMenuPrivs(@RequestParam(value = "roleId", required = true) Long roleId,
                             @RequestParam(value = "menuIds", required = true) Long[] menuIds) {
        try {
            sysRoleMenuService.addRoleMenus(roleId, menuIds);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.toString());
        }
    }

}
