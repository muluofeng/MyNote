package com.example.xing.dao;

import com.example.xing.entity.SysUser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 系统用户
 * 
 * @author chenxing
 * @email mail@chenxing.vip
 * @date 2019-01-01 01:57:06
 */
public interface SysUserDao extends BaseRepository<SysUser,Long> {

    /**
     * 查询用户所有权限
     *
     * @return
     */
    @Query(value = "select m.perms from tb_sys_user_role ur " +
            " LEFT JOIN tb_sys_role_menu rm on ur.role_id = rm.role_id " +
            " LEFT JOIN tb_sys_menu m on rm.menu_id = m.menu_id " +
            "where ur.user_id = :userId", nativeQuery = true)
    List<String> queryAllPerms(@Param("userId") Long userId);
}
