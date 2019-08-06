package com.example.xing.controller;

import com.example.xing.common.R;
import com.example.xing.form.SaveMenuForm;
import com.example.xing.service.SysMenuService;
import com.example.xing.shiro.ShiroUtils;
import com.example.xing.vo.SysMenuVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 菜单管理
 *
 * @author chenxing
 * @email mail@chenxing.vip
 * @date 2019-02-14 15:24:31
 */
@Slf4j
@RestController
@Api(value = "菜单管理", tags = {"菜单管理"})
@RequestMapping("admin/business/sysmenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取后台的树形菜单
     */
    @ApiOperation("获取后台的树形菜单")
    @GetMapping("/getMenu")
    @ResponseBody
//    @RequiresPermissions("business:sysmenu:list")
    public Object getMenu() {
        Long userId = ShiroUtils.getUserId();
        Assert.notNull(userId, "用户Id不能为空");

        //查询包含用户权限的
        Object roleMenuTree = sysMenuService.getRoleMenuTree(userId);

//         sysMenuService.getTreeMenu();
         return roleMenuTree;
    }

    /**
     * 新增菜单或修改菜单
     */
    @ApiOperation(value = "新增菜单或修改菜单", notes = "修改菜单时menuid不能为空")
    @PostMapping("/save")
    //@RequiresPermissions("business:sysmenu:save")
    public R save(SaveMenuForm form) {
        try {
            sysMenuService.save(form);
        } catch (Exception e) {
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("菜单删除")
    @PostMapping("/delete")
    //@RequiresPermissions("business:sysmenu:delete")
    public R delete(Long[] ids) {
        sysMenuService.delete(ids);

        return R.ok();
    }

    /**
     * 菜单管理列表
     */
    @ApiOperation("菜单管理列表")
    @PostMapping("/queryMenuTreeList")
    @ResponseBody
    public List<SysMenuVO> queryMenuTreeList() {
        try {
            List<SysMenuVO> queryTreeList = sysMenuService.findAllMenus();
            return (queryTreeList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
