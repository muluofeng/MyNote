package com.example.xing.service.impl;

import com.example.xing.dao.SysRoleMenuDao;
import com.example.xing.entity.SysRole;
import com.example.xing.entity.SysRoleMenu;
import com.example.xing.service.SysRoleMenuService;
import com.example.xing.service.SysRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenu, Long, SysRoleMenuDao>
        implements SysRoleMenuService {

    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysRoleMenuDao sysRoleMenuDao;

    @Override
    public void addRoleMenus(Long roleId, Long[] menuIds) throws Exception {
        SysRole role = sysRoleService.findOne(roleId);
        Assert.notNull(role, "角色不存在");
        // 先删除已存在的菜单
        sysRoleMenuDao.deleteRoleMenu(roleId);
        List<SysRoleMenu> entities = new ArrayList<>();
        for (Long menuId : menuIds) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            entities.add(roleMenu);
        }
        save(entities);
    }
}
