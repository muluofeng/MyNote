package com.example.demo.mapper;

import com.example.demo.model.MybatisUser;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author xiexingxing
 * @Created by 2019-10-01 15:20.
 */
public interface MybatisUserMapper {

    /**
     * 测试 association  查询
     *
     * @param id
     * @return
     */
    MybatisUser findById(Integer id);

    /**
     * 测试 association  分部查询
     *
     * @param id
     * @return
     */
    MybatisUser findByIdAssociationSelect(Integer id);

    /**
     * 根据部门id查询用户
     *
     * @param deptid
     * @return
     */
    List<MybatisUser> findByDeptId(Integer deptid);


    /**
     * 使用where 条件查询
     *
     * @param user
     * @return
     */
    MybatisUser selectByExample(MybatisUser user);

    /**
     * 使用 trim 代替where 条件查询
     *
     * @param user
     * @return
     */
    MybatisUser selectByExampleTrim(MybatisUser user);


    /**
     * 使用set  更新用户
     *
     * @param user
     * @return
     */
    int updateByExample(MybatisUser user);


    /**
     * 使用 trim 代替set  更新用户
     *
     * @param user
     * @return
     */
    int updateByExampleTrim(MybatisUser user);


    /**
     * 查询用户列表  list
     *
     * @param ids
     * @return
     */
    List<MybatisUser> selectByIds(List<Integer> ids);


    /**
     * 查询用户列表  array
     *
     * @param ids
     * @return
     */
    List<MybatisUser> selectByIdsArray(int[] ids);


    List<MybatisUser> selectByMap(@Param("example") Map map);
}