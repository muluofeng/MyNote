package com.example.xing.form;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: town
 * @description: 通用表单
 * @author: chenxing
 * @create: 2019-01-13 01:59
 **/
@Getter
@Setter
public class CommonForm {

    /**
     * 页码
     */
    int page = 1;

    /**
     * 每页数量
     */
    int pageSize = 10;

    /**
     * 排序字段
     */
    String sortField;

    /**
     * 排序类型 desc | asc
     */
    String sortType;
}
