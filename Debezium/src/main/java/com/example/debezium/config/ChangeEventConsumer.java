package com.example.debezium.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiexingxing
 * @date 2023/1/7 9:36 PM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ChangeEventConsumer implements DebeziumEngine.ChangeConsumer<ChangeEvent<String, String>> {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void handleBatch(List<ChangeEvent<String, String>> list,
            DebeziumEngine.RecordCommitter<ChangeEvent<String, String>> committer) throws InterruptedException {
        try {
            for (int i = 0; i <list.size(); i++) {
                ChangeEvent<String, String> record =  list.get(i);
                log.info("第{},接收到：{}",i,record);
//                if (Objects.nonNull(record.value())) {
//                    ValueStruct value = objectMapper.readValue(record.value(), ValueStruct.class);
//
//                    String table = Optional.ofNullable(value).map(v -> v.getPayload()).map(p -> p.getSource()).map(s -> s.getTable()).orElse(null);
//                    String op = Optional.ofNullable(value).map(v -> v.getPayload()).map(p -> p.getOp()).orElse(null);
//                    if (StringUtils.isNotBlank(op)) {
//                        log.info("表:{},操作类型:{}", table, op);
//
//                        Envelope.Operation operation = Envelope.Operation.forCode(op);
//                        switch (operation) {
//                            case CREATE:
//                                Optional.ofNullable(value).map(v -> v.getPayload()).ifPresent(x -> {
//                                    Map<String, Object> after = x.getAfter();
//                                    log.info("创建之后的数据:{}", after);
//                                });
//                                break;
//                            case UPDATE:
//                                Optional.ofNullable(value).map(v -> v.getPayload()).ifPresent(x -> {
//                                    Map<String, Object> after = x.getAfter();
//                                    log.info("更新之后的数据:{}", after);
//                                });
//                                break;
//                            case DELETE:
//                                Optional.ofNullable(value).map(v -> v.getPayload()).ifPresent(x -> {
//                                    Map<String, Object> before = x.getBefore();
//                                    log.info("删除之前的数据:{}", before);
//                                });
//                                break;
//                            case READ:
//                                break;
//                            default:
//                                ;
//                        }
//                    }
//                }

                // calling for each record
                committer.markProcessed(record);
            }
            // calling when this batch is finished
            committer.markBatchFinished();
        } catch (Exception e) {
            log.error("处理异常", e);
        }

    }
}