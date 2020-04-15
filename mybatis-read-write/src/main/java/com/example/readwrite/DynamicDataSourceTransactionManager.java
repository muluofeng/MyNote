package com.example.readwrite;

/**
 * @author xiexingxing
 * @Created by 2020-04-14 17:28.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import javax.sql.DataSource;

/**
 * 自定义事务管理器，继承自spring的事务管理器，只做了数据源的切换功能，其他全部继承父类
 * Created by mahengyang on 2017/7/24.
 */
public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager {
    protected static final Logger log = LoggerFactory.getLogger(DynamicDataSourceTransactionManager.class);

    public DynamicDataSourceTransactionManager() {
    }

    public DynamicDataSourceTransactionManager(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        boolean readOnly = definition.isReadOnly();
        if (readOnly) {
            log.debug("数据库事务管理器 读");
            DynamicDataSourceHolder.putDataSource(DynamicDataSourceHolder.MyDataSource.READ);
        } else {
            log.debug("数据库事务管理器 写");
            DynamicDataSourceHolder.putDataSource(DynamicDataSourceHolder.MyDataSource.WRITE);
        }
        super.doBegin(transaction, definition);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);
        DynamicDataSourceHolder.clearDataSource();
    }
}