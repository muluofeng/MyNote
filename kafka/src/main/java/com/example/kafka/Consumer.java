package com.example.kafka;

import com.example.kafka.common.DMLEnum;
import com.example.kafka.json.model.ExtField;
import com.example.kafka.json.model.KeyStruct;
import com.example.kafka.json.model.ValueStruct;
import com.example.kafka.json.parser.KakfaConsumerParserFactory;
import com.example.kafka.json.parser.kafkaConsumerParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import io.debezium.data.Envelope;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiexingxing
 * @Created by 2019-10-09 13:54.
 */
@Component
@Slf4j
public class Consumer {

    @Autowired
    private ObjectMapper debeObjectMapper;


    @Autowired
    private NamedParameterJdbcTemplate namedTemplate;

    //    /**
//     * 有消息就读取,读取消息topic，offset，key，value等信息
//     */
//    @KafkaListener(topics = "test")
//    public void listenT1(ConsumerRecord<?, ?> cr) {
//        System.out.println("消费数据： ");
//        System.out.println("listenT1收到消息,topic:>>>" + cr.topic()
//                + "  offset:>>" + cr.offset()
//                + "  key:>>" + cr.key()
//                + "  value:>>" + cr.value());
//    }
//
//
//
//
//    /**
//     * 有消息就读取,只读取消息value
//     */
//    @KafkaListener(topics = {"test13"})
//    public void receiveMessage(String message) {
//        //收到通道的消息之后执行秒杀操作
//        System.out.println("消费数据： ");
//        System.out.println(message);
//    }
//
//    /**
//     * 有消息就读取,批量读取消息value
//     */
//    @KafkaListener(topics = "test12")
//    public void onMessage(List<String> crs) {
//        System.out.println("消费数据： ");
//
//        for (String str : crs) {
//            System.out.println("test12:" + str);
//        }
//    }

    /**
     * 有消息就读取,读取消息topic，offset，key，value等信息
     */
//    @KafkaListener(topics = "ds14.mybatis-demo.users")
//    public void orecalTest(ConsumerRecord<?, ?> cr) {
//        System.out.println("orecal 测试消费数据： ");
//        System.out.println("listenT1收到消息,topic:>>>" + cr.topic()
//                + "  offset:>>" + cr.offset()
//                + "  key:>>" + cr.key()
//                + "  value:>>" + cr.value());
//    }


    @KafkaListener(id = "d1", topics = {"ds14.mybatis-demo.users"})
    public void listener(ConsumerRecord<String, String> record) throws Exception {
//        KeyStruct key = record.key();
//        ValueStruct value = record.value();
        if(record.value()==null){
            return;
        }
        KeyStruct key = debeObjectMapper.readValue(record.key(), KeyStruct.class);
        ValueStruct value =record.value()!=null?debeObjectMapper.readValue(record.value(), ValueStruct.class):null;
        log.info("Received record: key {}", key);
        log.info("Received record: value {}", value);

        String table = Optional.ofNullable(value).map(v -> v.getPayload()).map(p -> p.getSource()).map(s -> s.getTable()).orElse(null);
        String op = Optional.ofNullable(value).map(v -> v.getPayload()).map(p -> p.getOp()).orElse(null);
        Envelope.Operation operation = Envelope.Operation.forCode(op);

        log.info("table is {}, op is {}, operation is {}", table, op, operation);
        Map<String, Object> sqlParameterMap = Maps.newHashMap();
        List<String> preparedColumnList = Lists.newArrayList();
        List<String> preparedPrimaryKeyList = Lists.newArrayList();
        List<String> primaryKeyList = Lists.newArrayList();
        key.getSchema().getFields().stream().forEach(field -> {
            String primaryKey = field.getField();
            preparedPrimaryKeyList.add(primaryKey + "= :" + primaryKey);
            primaryKeyList.add(primaryKey);
            Object primaryKeyValue = key.getPayload().get(primaryKey);
            sqlParameterMap.put(primaryKey, parseColumnValue(field, primaryKeyValue));
        });

        // 获取数据库cdc事件之后的值
        Map<String, Object> after = Optional.ofNullable(value.getPayload()).map(p -> p.getAfter()).orElse(null);
        List<ExtField> fieldList = Optional.ofNullable(value.getSchema()).map(p -> p.getFields()).orElse(null);
        ExtField afterField = fieldList.stream().filter(f -> Objects.equals(f.getField(), "after"))
                .findFirst().orElse(null);


        // 处理表的列
        afterField.getFields().stream()
                .filter(field -> !primaryKeyList.contains(field.getField()))
                .forEach(field -> {
                    if(value.getPayload().getAfter()!=null){
                        String columnName = field.getField();
                        preparedColumnList.add(columnName + "= :" + columnName);
                        Object columnValue = value.getPayload().getAfter().get(columnName);
                        sqlParameterMap.put(columnName, parseColumnValue(field, columnValue));
                    }
                });

        log.info("sqlParameterMap {}", sqlParameterMap);


        String sql ="";

        if(!sqlParameterMap.isEmpty()&&Envelope.Operation.CREATE.code().equals(operation.code())){
            String insertKey = StringUtils.join(sqlParameterMap.keySet(),",");
            String insertValue =  StringUtils.join(sqlParameterMap.keySet().stream().map((s)-> ":"+s).collect(Collectors.toList()),",");
            sql = DMLEnum.INSERT_SQL.generateSQL(table, insertKey, insertValue);
            log.info("创建语句：{}",sql);
        }


        if(!sqlParameterMap.isEmpty()&&Envelope.Operation.UPDATE.code().equals(operation.code())){

            sql = DMLEnum.UPDATE_SQL.generateSQL(table, StringUtils.join(preparedColumnList, ","),
                    StringUtils.join(preparedPrimaryKeyList, " and "));
            log.info("更新语句：{}",sql);

        }

        if(!sqlParameterMap.isEmpty()&&Envelope.Operation.DELETE.code().equals(operation.code())){

            sql = DMLEnum.DELETE_SQL.generateSQL(table ,StringUtils.join(preparedPrimaryKeyList, " and "));
            log.info("删除语句：{}",sql);

        }

        namedTemplate.execute(sql, sqlParameterMap,new PreparedStatementCallback(){
            @Override
            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.execute();
                return null;
            }
        } );



    }

    protected Object parseColumnValue(ExtField field, Object value) {
        if (Objects.isNull(value)) {
            return null;
        }

        if (Objects.equals(field.getType(),"bytes")) {

            return value.toString().getBytes();
        }

        String schemaName = field.getName();
        kafkaConsumerParser parser = KakfaConsumerParserFactory.getParser(schemaName);
        if (Objects.nonNull(parser)) {
            return parser.parse(field, value);
        }
        return value;
    }





}