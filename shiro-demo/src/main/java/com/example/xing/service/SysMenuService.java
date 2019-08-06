package com.example.xing.service;

import com.example.xing.dao.SysMenuDao;
import com.example.xing.entity.SysMenu;
import com.example.xing.form.SaveMenuForm;
import com.example.xing.vo.SysMenuVO;

import java.util.List;

/**
 * 菜单管理
 *
 * @author chenxing
 * @email mail@chenxing.vip
 * @date 2019-02-14 15:24:31
 */
public interface SysMenuService extends BaseService<SysMenu, Long, SysMenuDao> {

    /**
     * 获取无权限的树形菜单
     *
     * @return
     */
    Object getTreeMenu();

    /**
     * 获取有权限的菜单树
     *
     * @param userId
     * @return
     */
    Object getRoleMenuTree(Long userId);

    List<SysMenuVO> findAllMenus();

    void save(SaveMenuForm form) throws Exception;

}

