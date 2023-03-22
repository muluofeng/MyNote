
package com.example.debezium.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.debezium.config.Configuration;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
@org.springframework.context.annotation.Configuration
public class DebeziumConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper map = new ObjectMapper();
        map.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        map.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return map;
    }

    public Configuration configuration() {

        return Configuration.create()
                .with("name", "embedded-engine")
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")

//                .with("database.history.kafka.bootstrap.servers", "localhost:9092")
//                .with("database.history.kafka.topic", "my-app-connector-history")


                //offset config begin - 使用文件来存储已处理的binlog偏移量
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", "/Users/muluofeng/workspace/java/MyNote/Debezium/src/main/resources/offsets/mysql_offsets.dat")
                .with("offset.flush.interval.ms", 0)
                //offset config end

                .with("database.hostname", "localhost")
                .with("database.port", "3306")
                .with("database.user", "root")
                .with("database.password", "Xing0830")
                .with("database.server.id", "1")  //需要与MySQL的server-id不同
                .with("database.server.name", "my-app-connector")
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.file.filename", "/Users/muluofeng/workspace/java/MyNote/Debezium/src/main/resources/dbhistory/dbhistory.dat")
                .with("database.include.list", "test") //要捕获的数据库名
                .with("table.include.list", "test.users")//要捕获的数据表
//                .with("table.include.list", "test.user,test.my_range_timestamp")//要捕获的数据表
//                .with("database.serverTimezone","UTC")//时区
                .with("database.connectionTimeZone", "Asia/Shanghai")
//                .with( "snapshot.select.statement.overrides","test.my_range_timestamp")
//                .with( "snapshot.select.statement.overrides.test.my_range_timestamp","select * from test.my_range_timestamp where id > 5")
//                .with( "snapshot.new.tables","parallel")


                .with("snapshot.mode", "initial")  //全量+增量
                .with("poll.interval.ms", 1000)
                .with("include.schema.changes", false)


                .with("converters", DateTimeConverter.CONVERTERS_NAME)
                .with(DateTimeConverter.CONVERTERS_TYPE, DateTimeConverter.class.getCanonicalName())
                .build();


    }

    @Bean
    public DebeziumEngine<ChangeEvent<String,String>> debeziumEngine( ChangeEventConsumer changeEventConsumer) {
        DebeziumEngine<ChangeEvent<String, String>> debeziumEngine = DebeziumEngine.create(Json.class)
                .using(configuration().asProperties())
                .notifying(changeEventConsumer)
                .build();
        return debeziumEngine;

    }



    @Bean
    DebeziumServerBootstrap DebeziumServerBootstrap(DebeziumEngine debeziumEngine ) {
        DebeziumServerBootstrap debeziumServerBootstrap = new DebeziumServerBootstrap();
        debeziumServerBootstrap.setDebeziumEngine(debeziumEngine);
        return debeziumServerBootstrap;
    }


}
