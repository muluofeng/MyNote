package com.example.readwrite;

/**
 * @author xiexingxing
 * @Created by 2020-04-14 17:27.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据MyDynamicDataSourceHolder中的线程局部变量，选择数据源（读/写）
 * Created by mahengyang on 2017/7/21.
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    protected static final Logger log = LoggerFactory.getLogger(DynamicRoutingDataSource.class);

    private Object read;
    private Object write;

    public DynamicRoutingDataSource(Object read, Object write) {
        this.read = read;
        this.write = write;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        DynamicDataSourceHolder.MyDataSource dataSource = DynamicDataSourceHolder.getDataSource();
        if (dataSource == null || dataSource == DynamicDataSourceHolder.MyDataSource.WRITE) {
            log.debug("动态路由数据源 本次选择使用 写 数据源");
            return DynamicDataSourceHolder.MyDataSource.WRITE.name();
        }
        log.debug("动态路由数据源 本次选择使用 读 数据源");
        return DynamicDataSourceHolder.MyDataSource.READ.name();
    }

    /**
     * 把数据源配置到spring的管理器中，因为spring的数据源是用Map<Object,Object>存储的，所以这里以枚举的名称作为key，数据源作为value
     */
    @Override
    public void afterPropertiesSet() {
        if (this.write == null) {
            throw new IllegalArgumentException("Property 'write' is required");
        }
        setDefaultTargetDataSource(write);
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSourceHolder.MyDataSource.WRITE.name(), write);
        if (read != null) {
            targetDataSources.put(DynamicDataSourceHolder.MyDataSource.READ.name(), read);
        }
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    public void setRead(Object read) {
        this.read = read;
    }

    public void setWrite(Object write) {
        this.write = write;
    }
}