package com.example.readwrite;

/**
 * @author xiexingxing
 * @Created by 2020-04-14 17:29.
 */

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mybatis插件，根据sql类型判断应该使用哪种数据源（读/写），如果是数据库事务，则不处理
 * Created by mahengyang on 2017/7/21.
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class DynamicDataSourcePlugin implements Interceptor {
    protected static final Logger log = LoggerFactory.getLogger(DynamicDataSourcePlugin.class);
    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    private static final Map<String, DynamicDataSourceHolder.MyDataSource> cacheMap = new ConcurrentHashMap<>();

//    [@Override](https://my.oschina.net/u/1162528)
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 判断是否是数据库事务
        boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
        log.debug("动态数据源插件 是否是事务:{}", synchronizationActive);
        if (!synchronizationActive) {
            Object[] objects = invocation.getArgs();
            MappedStatement ms = (MappedStatement) objects[0];
            String statementId = ms.getId();
            DynamicDataSourceHolder.MyDataSource dataSource = cacheMap.get(statementId);
            log.debug("动态数据源插件 非事务 数据源:{}", dataSource);
            if (dataSource == null) {
                //读方法
                if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                    //!selectKey 为自增id查询主键(SELECT LAST_INSERT_ID() )方法，使用主库
                    if (statementId.contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                        dataSource = DynamicDataSourceHolder.MyDataSource.WRITE;
                    } else {
                        BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                        String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
                        if (sql.matches(REGEX)) {
                            dataSource = DynamicDataSourceHolder.MyDataSource.WRITE;
                        } else {
                            dataSource = DynamicDataSourceHolder.MyDataSource.READ;
                        }
                    }
                } else {
                    // 写方法
                    dataSource = DynamicDataSourceHolder.MyDataSource.WRITE;
                }
                log.debug("读写分离插件 设置方法:{} 使用:{} 数据源 SqlCommandType [{}]", statementId, dataSource.name(), ms.getSqlCommandType().name());
                cacheMap.put(statementId, dataSource);
            }
            DynamicDataSourceHolder.putDataSource(dataSource);
        }
        return invocation.proceed();
    }

//    [@Override](https://my.oschina.net/u/1162528)
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}