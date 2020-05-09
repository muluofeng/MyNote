package com.example.kafka.json.parser;

import com.example.kafka.json.model.ExtField;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-04-22 11:06
 */
@Slf4j
public class ZonedTimestampParser implements kafkaConsumerParser<Object, String> {

    @Override
    public String parse(ExtField field, Object input) {
        try {
            Date date = DateFormatUtils.ISO_DATETIME_FORMAT.parse(input.toString());
            return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            log.error("解析ZonedTimestamp失败，", e);
        }
        return null;
    }
}
