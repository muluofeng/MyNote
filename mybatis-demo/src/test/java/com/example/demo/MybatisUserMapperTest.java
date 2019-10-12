package com.example.demo;

import com.example.demo.mapper.MybatisDeptMapper;
import com.example.demo.mapper.MybatisUserMapper;
import com.example.demo.model.MybatisDept;
import com.example.demo.model.MybatisUser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

/**
 * @author xiexingxing
 * @Created by 2019-06-02 10:54 PM.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisUserMapperTest {

    @Autowired
    private MybatisUserMapper mybatisUserMapper;

    @Autowired
    private MybatisDeptMapper mybatisDeptMapper;

    @Test
    public void findById() throws Exception {
        MybatisUser user = mybatisUserMapper.findById(1);
        System.out.println(user.getDept());
    }


    @Test
    public void findByIdAssociationSelect() throws Exception {
        MybatisUser user = mybatisUserMapper.findByIdAssociationSelect(1);
        System.out.println(user.getDept());
    }


    @Test
    public void findDeptCollection() throws Exception {
        MybatisDept dept = mybatisDeptMapper.findDeptCollection(1);
        System.out.println(dept.getUser());
    }

    @Test
    public void findDeptCollectionByStep() throws Exception {
        MybatisDept dept = mybatisDeptMapper.findDeptCollectionByStep(1);
        System.out.println(dept.getUser());
    }

    @Test
    public void selectByExample() throws Exception {
        MybatisUser user = new MybatisUser();
        user.setName("%张%");
//        MybatisUser dept = mybatisUserMapper.selectByExample(user);
        MybatisUser dept = mybatisUserMapper.selectByExampleTrim(user);
        System.out.println(dept.getName());
    }


    @Test
    public void updateById() throws Exception {
        MybatisUser user = new MybatisUser();
        user.setId(1);
        user.setName("张三3");
//        mybatisUserMapper.updateByExample(user);
        mybatisUserMapper.updateByExampleTrim(user);
    }

    @Test
    public void selectByIds() throws Exception {
//        List<MybatisUser> mybatisUsers = mybatisUserMapper.selectByIds(new ArrayList<>(Arrays.asList(1, 2)));
        List<MybatisUser> mybatisUsers = mybatisUserMapper.selectByIdsArray(new int[]{1, 2});
        System.out.println(mybatisUsers.size());
    }

    @Test
    public void selectByMap() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("name", "张三3");
        List<MybatisUser> mybatisUsers = mybatisUserMapper.selectByMap(map);
        System.out.println(mybatisUsers.size());
    }


}
