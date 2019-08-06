package com.example.xing.dto.mapper;

import com.example.xing.dto.SysUserDTO;
import com.example.xing.entity.SysUser;
import com.example.xing.form.SysUserForm;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

/**
 * @program: town
 * @description: 管理员实体对象转换 ,赋值时检测是否空值
 * @author: chenxing
 * @create: 2019-01-15 22:51
 **/
@Mapper(componentModel = "spring")
public interface SysUserMapper {

    /**
     * 表单转实体对象, 忽略密码转换
     *
     * @param form
     * @return
     */
    @Mappings({
            @Mapping(target = "password", ignore = true),
    })
    SysUser from(SysUserForm form);

    /**
     * 表单转实体对象, 忽略密码转换
     *
     * @param user
     * @return
     */
    SysUserDTO toDTO(SysUser user);

    /**
     * 复制属性值
     *
     * @param from
     * @param to
     */
    @Mappings({
            @Mapping(target = "password", ignore = true),
    })
    void copyTo(SysUserForm from, @MappingTarget SysUser to);
}
