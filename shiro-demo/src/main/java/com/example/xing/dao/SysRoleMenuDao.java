package com.example.xing.dao;

import com.example.xing.entity.SysRoleMenu;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 角色与菜单对应关系
 * @author chenxing
 * @email mail@chenxing.vip
 * @date 2019-01-01 01:57:06
 */
public interface SysRoleMenuDao extends BaseRepository<SysRoleMenu, Long> {

    /**
     * 删除角色的菜单
     * @author 刘在飞
     * @Description
     * @param roleId void
     * @date 2019年3月14日 下午6:23:18
     * @modifitor 刘在飞
     * @reason
     */
    @Modifying
    @Query(value = "delete from tb_sys_role_menu where role_id = :roleId", nativeQuery = true)
    void deleteRoleMenu(@Param("roleId") Long roleId);

}
