package com.example.xing.common;

import com.example.xing.form.CommonForm;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @program: town
 * @description: 分页请求, 解决spring page 默认从0开始不合常理的
 * @author: chenxing
 * @create: 2019-01-01 00:51
 **/
public class PageableRequest extends PageRequest {

    @Deprecated
    public PageableRequest(int page, int size, Sort sort) {
        super(page - 1, size);
    }

    /**
     * Creates a new {@link PageRequest} with sort parameters applied.
     *
     * @param page zero-based page index.
     * @param size the size of the page to be returned.
     * @param sort must not be {@literal null}.
     * @since 2.0
     */
    @SuppressWarnings(value = "deprecation")
    public static PageRequest of(int page, int size, Sort sort) {
        return new PageRequest(page - 1, size, sort);
    }

    /**
     * 获得分页信息对象
     *
     * @param page
     * @param size
     * @return
     */
    public static PageRequest of(int page, int size) {
        return of(page, size, Sort.unsorted());
    }

    /**
     * 根据表单信息组装分页请求对象
     *
     * @param form
     * @return
     */
    public static PageRequest of(CommonForm form) {

        Sort orders;

        //根据表单传递 获得排序字段和类型
        if (StringUtils.isNotEmpty(form.getSortField()) &&
                StringUtils.isNotEmpty(form.getSortType())) {

            orders = new Sort(Sort.Direction.DESC.name().equalsIgnoreCase(form.getSortType()) ?
                    Sort.Direction.DESC : Sort.Direction.ASC, form.getSortField());
        } else {
            orders = Sort.unsorted();
        }

        return of(form.getPage(), form.getPageSize(), orders);
    }

}
