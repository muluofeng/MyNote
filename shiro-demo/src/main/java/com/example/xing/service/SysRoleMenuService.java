package com.example.xing.service;

import com.example.xing.dao.SysRoleMenuDao;
import com.example.xing.entity.SysRoleMenu;

/**
 * 角色与菜单对应关系
 * @author chenxing
 * @email mail@chenxing.vip
 * @date 2019-01-01 01:57:06
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenu, Long, SysRoleMenuDao> {

    /**
     * 添加角色菜单
     * @author 刘在飞
     * @Description
     * @param roleId
     * @param menuIds
     * @throws Exception void
     * @date 2019年3月14日 下午6:25:40
     * @modifitor 刘在飞
     * @reason
     */
    void addRoleMenus(Long roleId, Long[] menuIds) throws Exception;

}
