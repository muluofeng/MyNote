package com.example.xing.service;

import com.example.xing.dao.BaseRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: town
 * @description:
 * @author: chenxing
 * @create: 2018-12-31 20:03
 **/
public interface BaseService<T, ID extends Serializable, DAO extends BaseRepository> {

    /**
     * 根据条件差查询并分页
     *
     * @param predicate
     * @param pageable
     * @return
     */
    Page<T> findPage(com.querydsl.core.types.Predicate predicate, Pageable pageable);

    /**
     * 根据条件差查询并分页
     *
     * @param example
     * @param pageable
     * @return
     */
    Page<T> findPage(Example<T> example, Pageable pageable);

    /**
     * 根据条件差查询并分页
     *
     * @param pageable
     * @return
     */
    Page<T> findPage(Pageable pageable);

    /**
     * 根据条件差查询
     *
     * @param predicate
     * @return
     */
    List<T> findAll(Predicate predicate);

    /**
     * 根据条件差+排序 查询
     *
     * @param predicate
     * @return
     */
    List<T> findAll(Predicate predicate, Sort sort);

    /**
     * 排序 查询
     * @param sort
     * @return
     */
    List<T> findAll(Sort sort);

    /**
     * 查找所有数据
     *
     * @return
     */
    List<T> findAll();

    /**
     * 数据保存操作
     *
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * 批量数据保存操作
     *
     * @param entities 待批量操作数据集合
     * @return
     */
    List<T> save(Iterable<T> entities);

    /**
     * 基于主键查询单一数据对象
     *
     * @param id
     * @return
     */
    T findOne(ID id);

    /**
     * Querydsl  查询
     *
     * @param predicate
     * @return
     */
    T findOne(Predicate predicate);

    /**
     * 数据删除操作
     *
     * @param entity 待操作数据
     */
    void delete(T entity);

    /**
     * 数据删除操作
     *
     * @param id
     */
    void delete(ID id);

    /**
     * 数据删除操作
     *
     * @param ids
     */
    void delete(ID... ids);

    /**
     * @param entities 待批量操作数据集合
     * @return
     */
    void delete(Iterable<T> entities);

    /**
     * 根据泛型对象属性和值查询唯一对象
     *
     * @param property 属性名，即对象中数量变量名称
     * @param value    参数值
     * @return 未查询到返回null，如果查询到多条数据则抛出异常
     */
    T findByProperty(final String property, final Object value);

    /**
     * 根据泛型对象属性和值查询唯一对象
     *
     * @param property 属性名，即对象中数量变量名称
     * @param value    参数值
     * @return 未查询到返回null，如果查询到多条数据则返回第一条
     */
    T findFirstByProperty(final String property, final Object value);

    /**
     * 基于动态组合条件对象和分页(含排序)对象查询数据集合
     *
     * @param example
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    Page<T> findByPage(Example<T> example, Pageable pageable);

    /**
     * 基于JPA通用的查询条件count记录数据
     *
     * @param spec
     * @return
     */
    long count(Specification<T> spec);

    /**
     * 帮助类方法，方便获取HttpServletRequest
     *
     * @return
     */
    HttpServletRequest getRequest();


    /**
     * 获取JPAQueryFactory
     * @return
     */
    JPAQueryFactory getQueryFactory();
}
