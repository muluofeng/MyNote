package com.example.xing.dto.mapper;

import com.example.xing.entity.SysMenu;
import com.example.xing.form.SaveMenuForm;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SysMenuMapper {

    SysMenu from(SaveMenuForm form);

    /**
     * 复制数据到菜单
     *
     * @param form
     * @param to
     */
    void copyTo(SaveMenuForm form, @MappingTarget SysMenu to);
}
