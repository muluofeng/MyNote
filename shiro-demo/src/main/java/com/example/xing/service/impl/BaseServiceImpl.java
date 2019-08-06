package com.example.xing.service.impl;

import com.example.xing.dao.BaseRepository;
import com.example.xing.service.BaseService;
import com.google.common.collect.Lists;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @program: town
 * @description:
 * @author: chenxing
 * @create: 2018-12-31 17:34
 **/

@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BaseServiceImpl<T, ID extends Serializable, DAO extends BaseRepository> implements BaseService<T, ID, DAO> {

    /**
     * 数据持久对象
     */
    @Autowired
    public DAO dao;
    public JPAQueryFactory queryFactory;
    @Autowired
    EntityManager em;

    public JPAQueryFactory getQueryFactory() {
        if (queryFactory == null) {
            queryFactory = new JPAQueryFactory(em);
        }
        return queryFactory;
    }

    /**
     * 分页返回分页
     *
     * @param pageable
     * @return
     */
    public Page<T> findPage(com.querydsl.core.types.Predicate predicate, Pageable pageable) {
        if (null != predicate) {
            return dao.findAll(predicate, pageable);
        } else {
            return dao.findAll(pageable);
        }
    }

    /**
     * 分页返回分页
     *
     * @param pageable
     * @return
     */
    public Page<T> findPage(Example<T> example, Pageable pageable) {
        return dao.findAll(example, pageable);
    }

    /**
     * 根据条件差查询并分页
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<T> findPage(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public List<T> findAll(com.querydsl.core.types.Predicate predicate) {
        Iterable<T> all = dao.findAll(predicate);
        return Lists.newArrayList(all);
    }

    @Override
    public List<T> findAll(com.querydsl.core.types.Predicate predicate, Sort sort) {
        Iterable<T> all = dao.findAll(predicate, sort);
        return Lists.newArrayList(all);
    }

    /**
     * 排序 查询
     *
     * @param sort
     * @return
     */
    @Override
    public List<T> findAll(Sort sort) {
        return dao.findAll(sort);
    }

    /**
     * 查找所有数据
     *
     * @return
     */
    @Override
    public List<T> findAll() {
        return dao.findAll();
    }

    /**
     * 数据保存操作
     *
     * @param entity
     * @return
     */
    @Override
    public T save(T entity) {
        return (T) dao.save(entity);
    }

    /**
     * 批量数据保存操作
     *
     * @param entities 待批量操作数据集合
     * @return
     */
    public List<T> save(Iterable<T> entities) {
        return dao.saveAll(entities);
    }

    /**
     * 基于主键查询单一数据对象
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public T findOne(ID id) {
        Assert.notNull(id, "ID不能为空");
        Optional<T> optional = dao.findById(id);

        return optional.isPresent() ? optional.get() : null;
    }

    /**
     * Querydsl  查询
     *
     * @param predicate
     * @return
     */
    @Transactional(readOnly = true)
    public T findOne(com.querydsl.core.types.Predicate predicate) {
        Optional<T> optional = dao.findOne(predicate);
        return optional.isPresent() ? optional.get() : null;
    }

    /**
     * 数据删除操作
     *
     * @param entity 待操作数据
     */
    public void delete(T entity) {
        dao.delete(entity);
    }

    public void delete(ID id) {
        dao.deleteById(id);
    }

    public void delete(ID... ids) {
        for (ID id : ids) {
            dao.deleteById(id);
        }
    }

    /**
     * @param entities 待批量操作数据集合
     * @return
     */
    public void delete(Iterable<T> entities) {
        dao.deleteInBatch(entities);
    }

    /**
     * 根据泛型对象属性和值查询唯一对象
     *
     * @param property 属性名，即对象中数量变量名称
     * @param value    参数值
     * @return 未查询到返回null，如果查询到多条数据则抛出异常
     */
    public T findByProperty(final String property, final Object value) {

        List<T> entities = this.dao.findAll(new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                @SuppressWarnings("rawtypes")
                Path expression = root.get(property);
                return builder.equal(expression, value);
            }
        });
        if (CollectionUtils.isEmpty(entities)) {
            return null;
        } else {
            Assert.isTrue(entities.size() == 1, "返回数据长度不正确");
            return entities.get(0);
        }
    }

    /**
     * 根据泛型对象属性和值查询唯一对象
     *
     * @param property 属性名，即对象中数量变量名称
     * @param value    参数值
     * @return 未查询到返回null，如果查询到多条数据则返回第一条
     */
    public T findFirstByProperty(final String property, final Object value) {
        Specification<T> spec = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                @SuppressWarnings("rawtypes")
                Path expression = root.get(property);
                return builder.equal(expression, value);
            }
        };

        List<T> entities = dao.findAll(spec);
        if (CollectionUtils.isEmpty(entities)) {
            return null;
        } else {
            return entities.get(0);
        }
    }

    /**
     * 基于动态组合条件对象和分页(含排序)对象查询数据集合
     *
     * @param example
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<T> findByPage(Example<T> example, Pageable pageable) {
        return dao.findAll(example, pageable);
    }

    /**
     * 基于JPA通用的查询条件count记录数据
     *
     * @param spec
     * @return
     */
    @Transactional(readOnly = true)
    public long count(Specification<T> spec) {
        return dao.count(spec);
    }

    /**
     * 基于JPA通用的查询条件count记录数据
     *
     * @param spec
     * @param predicate
     * @return
     */
    @Transactional(readOnly = true)
    public long count(com.querydsl.core.types.Predicate predicate) {
        return dao.count(predicate);
    }

    /**
     * 帮助类方法，方便获取HttpServletRequest
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

}
