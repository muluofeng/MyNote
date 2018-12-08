package com.example.xing;

/**
 * 数据库常量
 * ˚
 *
 * @program: gcwe
 * @description:
 * @author: chenxing
 * @create: 2018-11-16 18:28
 **/
public class DBConstant {

    /**
     * 数据库名前缀
     */
    public final static String DB_PREFIX = "tenant_";

    /**
     * 从请求中获取的租户key
     */
    public final static String TENANT_KEY = "tenantKey";

    /**
     * 默认域名
     */
    public final static String DEFAULT_DOMAIN = "manager";

    /**
     * 默认租户 (默认数据库)
     */
    public final static String DEFAULT_TENANT = DB_PREFIX + DEFAULT_DOMAIN;


    /**
     * 当前企业信息Key
     */
    public final static String CURRENT_ENTERPRISE_KEY = "enterprise_key";


}
