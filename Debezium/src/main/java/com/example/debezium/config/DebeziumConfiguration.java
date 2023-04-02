
package com.example.debezium.config;


import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Properties;

@Slf4j
@Configuration
public class DebeziumConfiguration {
    @Bean("debeziumMysqlProperties")
    public Properties configuration() {
        Properties props = new Properties();
        try {
            props.load(DebeziumConfiguration.class.getClassLoader().getResourceAsStream("debezium-mysql.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return props;

        //                .with("database.history", "io.debezium.storage.file.history.FileSchemaHistory")
//                .with("database.history.file.filename", "/Users/muluofeng/workspace/java/MyNote/Debezium/src/main/resources/dbhistory/dbhistory.dat")

        //                .with("table.include.list", "test.user,test.my_range_timestamp")//要捕获的数据表
//                .with("database.connectionTimeZone", "Asia/Shanghai")
//                .with( "snapshot.select.statement.overrides","test.my_range_timestamp")
//                .with( "snapshot.select.statement.overrides.test.my_range_timestamp","select * from test.my_range_timestamp where id > 5")
//                .with( "snapshot.new.tables","parallel")
                //                .with("database.history.kafka.bootstrap.servers", "localhost:9092")
//                .with("database.history.kafka.topic", "my-app-connector-history")




    }

//    @Bean
//    public DebeziumEngine<ChangeEvent<String,String>> debeziumEngine( ChangeEventJsonConsumer changeEventConsumer) {
//        DebeziumEngine<ChangeEvent<String, String>> debeziumEngine = DebeziumEngine.create(Json.class)
//                .using(configuration())
//                .notifying(changeEventConsumer)
//                .build();
//        return debeziumEngine;
//    }

    @Bean
    public DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine(  ) {
        DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(configuration())
                .notifying(new ChangeEventConsumer())
                .build();

        return debeziumEngine;
    }




}
