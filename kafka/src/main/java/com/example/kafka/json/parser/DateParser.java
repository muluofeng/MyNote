package com.example.kafka.json.parser;


import com.example.kafka.json.model.ExtField;

import java.time.LocalDate;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-04-22 11:06
 */
public class DateParser implements kafkaConsumerParser<Object, LocalDate> {

    @Override
    public LocalDate parse(ExtField field, Object value) {
        return LocalDate.ofEpochDay((long) (int) value + 1);
    }
}
