package com.example.kafka.json.parser;

import com.example.kafka.json.model.ExtField;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-04-22 11:08
 */
@FunctionalInterface
public interface kafkaConsumerParser<T, R> {

    R parse(ExtField field, T t);

}
