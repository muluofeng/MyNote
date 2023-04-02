package com.example.debezium.config;


import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.ChangeEventFormat;
import lombok.Data;
import lombok.SneakyThrows;
import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author n1
 * @since 2021/6/2 10:45
 */
@Data
@Component
public class DebeziumServerBootstrap {

    private final Executor executor = Executors.newSingleThreadExecutor();

    @Autowired
    private DebeziumEngine<?> debeziumEngine;


    @Autowired
    private Properties debeziumMysqlProperties;


    @PostConstruct
    public void start() {
        this.executor.execute(debeziumEngine);
    }

    @SneakyThrows
    @PreDestroy
    public void stop() {
        if (debeziumEngine != null) {
            debeziumEngine.close();
        }
    }

    public void restartWithNewTable(String dbName, String tableName) {
        String includeTableStr = "table.include.list";
        String includeTableValueStr = debeziumMysqlProperties.getProperty(includeTableStr);
        List<String> tables = Lists.newArrayList(Arrays.asList(includeTableValueStr.split(",")));
        String newTable =  dbName + "." + tableName;
        if(tables.contains(newTable)){
            return;
        }
        String value = includeTableValueStr + "," +newTable;
        debeziumMysqlProperties.setProperty(includeTableStr,value);
        this.stop();
        this.debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class)).using(debeziumMysqlProperties)
                .notifying(new ChangeEventConsumer()).build();
        this.start();

    }
}
