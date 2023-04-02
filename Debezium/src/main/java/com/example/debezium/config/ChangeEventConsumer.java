package com.example.debezium.config;

import io.debezium.data.Envelope;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static io.debezium.data.Envelope.FieldName.AFTER;
import static io.debezium.data.Envelope.FieldName.BEFORE;
import static io.debezium.data.Envelope.FieldName.OPERATION;
import static io.debezium.data.Envelope.FieldName.SOURCE;
import static java.util.stream.Collectors.toMap;

/**
 * @author xiexingxing
 * @date 2023/3/23 9:00 PM
 */
@Slf4j
public class ChangeEventConsumer implements DebeziumEngine.ChangeConsumer<RecordChangeEvent<SourceRecord>> {

    private  AtomicInteger num  = new AtomicInteger(0);
    @SneakyThrows
    @Override
    public void handleBatch(List<RecordChangeEvent<SourceRecord>> list,
            DebeziumEngine.RecordCommitter<RecordChangeEvent<SourceRecord>> recordCommitter) throws InterruptedException {
        try {
            log.info("handleBatch size:{}",list.size());
            for (int i = 0; i < list.size(); i++) {
                RecordChangeEvent<SourceRecord> r = list.get(i);
                SourceRecord sourceRecord = r.record();
                log.info(sourceRecord.toString());
                Struct sourceRecordChangeValue = (Struct) sourceRecord.value();

                if (sourceRecordChangeValue != null) {
                    // 判断操作的类型 过滤掉读 只处理增删改   这个其实可以在配置中设置
                    Field ddlField = sourceRecordChangeValue.schema().field("ddl");
                    if (Objects.nonNull(ddlField)) {
                        //暂时不处理ddl
                        markProcessed(recordCommitter, r);
                        continue;
                    }
                    Envelope.Operation operation = Envelope.Operation.forCode((String) sourceRecordChangeValue.get(OPERATION));

                    if (operation != Envelope.Operation.READ) {

                        String record = operation == Envelope.Operation.DELETE ? BEFORE : AFTER;
                        // 获取增删改对应的结构体数据
                        Struct struct = (Struct) sourceRecordChangeValue.get(record);

                        Struct source = (Struct) sourceRecordChangeValue.get(SOURCE);
                        Map<String, Object> sources = source.schema().fields().stream().map(Field::name)
                                .filter(fieldName -> source.get(fieldName) != null).map(fieldName -> Pair.of(fieldName, source.get(fieldName)))
                                .collect(toMap(Pair::getKey, Pair::getValue));

                        Map<String, Object> payload = struct.schema().fields().stream().map(Field::name)
                                .filter(fieldName -> struct.get(fieldName) != null).map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
                                .collect(toMap(Pair::getKey, Pair::getValue));
                        // 这里简单打印一下
                        String name = struct.schema().name();
                        log.info("operation = " + operation.name());
                        log.info("payload = " + payload);
                        log.info("db.table = " + sources.get("db") +"." + sources.get("table"));

                        markProcessed(recordCommitter, r);
                    } else {
                        //读取数据
                    }
                }

            }
        } catch (Exception e) {
            log.error("handleBatch error", e);
        }
        recordCommitter.markBatchFinished();
    }

    private static void markProcessed(DebeziumEngine.RecordCommitter<RecordChangeEvent<SourceRecord>> recordCommitter,
            RecordChangeEvent<SourceRecord> r) {
        try {
            recordCommitter.markProcessed(r);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
