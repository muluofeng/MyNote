package com.example.debezium.config;


import io.debezium.engine.DebeziumEngine;
import lombok.Data;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author n1
 * @since 2021/6/2 10:45
 */
@Data
public class DebeziumServerBootstrap {

    private final Executor executor = Executors.newSingleThreadExecutor();


    private DebeziumEngine<?> debeziumEngine;


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
}
