package com.example.debezium.config;

/**
 * @author xiexingxing
 * @date 2023/1/7 9:51 PM
 */

import io.debezium.spi.converter.CustomConverter;
import io.debezium.spi.converter.RelationalColumn;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.data.SchemaBuilder;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Properties;

/**
 * 自定义日期时间类型转换器
 */
@Slf4j
public class DateTimeConverter implements CustomConverter<SchemaBuilder, RelationalColumn> {
    public final static String CONVERTERS_NAME = "datetime";
    public final static String CONVERTERS_TYPE = CONVERTERS_NAME + ".type";

    public final static String FORMATTER_PATTERN_TIME = buildFullPropertyName("formatter.pattern.time");
    public final static String FORMATTER_PATTERN_DATE = buildFullPropertyName("formatter.pattern.date");
    public final static String FORMATTER_PATTERN_DATETIME = buildFullPropertyName("formatter.pattern.datetime");
    public final static String FORMATTER_PATTERN_TIMESTAMP = buildFullPropertyName("formatter.pattern.timestamp");
    public final static String FORMATTER_PATTERN_TIMESTAMP_ZONID = buildFullPropertyName("formatter.pattern.timestamp.zoneid");

    private DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_TIME;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    private DateTimeFormatter timestampFormatter = DateTimeFormatter.ISO_DATE_TIME;
    private ZoneId timestampZoneId = ZoneId.systemDefault();

    private static String buildFullPropertyName(String shortPropertyName) {
        return CONVERTERS_NAME + "." + shortPropertyName;
    }

    private static String buildShortPropertyName(String fullPropertyName) {
        return fullPropertyName.replaceFirst(CONVERTERS_NAME + ".", "");
    }

    @Override
    public void configure(Properties properties) {
        Optional.ofNullable(properties.getProperty(buildShortPropertyName(FORMATTER_PATTERN_TIME)))
                .ifPresent(f -> timeFormatter = DateTimeFormatter.ofPattern(f));
        Optional.ofNullable(properties.getProperty(buildShortPropertyName(FORMATTER_PATTERN_DATE)))
                .ifPresent(f -> dateFormatter = DateTimeFormatter.ofPattern(f));
        Optional.ofNullable(properties.getProperty(buildShortPropertyName(FORMATTER_PATTERN_DATETIME)))
                .ifPresent(f -> dateTimeFormatter = DateTimeFormatter.ofPattern(f));
        Optional.ofNullable(properties.getProperty(buildShortPropertyName(FORMATTER_PATTERN_TIMESTAMP)))
                .ifPresent(f -> timestampFormatter = DateTimeFormatter.ofPattern(f));
        Optional.ofNullable(properties.getProperty(buildShortPropertyName(FORMATTER_PATTERN_TIMESTAMP_ZONID)))
                .ifPresent(z -> timestampZoneId = ZoneId.of(z));
    }

    @Override
    public void converterFor(RelationalColumn relationalColumn, ConverterRegistration<SchemaBuilder> converterRegistration) {
        String columnType = relationalColumn.typeName().toUpperCase();
        Converter converter = null;

        switch (columnType) {
            case "TIME":
                converter = (x) -> {
                    if(x==null){
                        return null;
                    }
                    if (x instanceof Duration)
                        return LocalTime.ofNanoOfDay(((Duration) x).toNanos()).format(timeFormatter);
                    if (x instanceof String)
                        return LocalTime.ofNanoOfDay(Duration.parse((String) x).toNanos()).format(timeFormatter);

                    log.warn("[DateTimeConverter] unhandled column type({}), current type({})", columnType, x.getClass()
                            .getName());
                    return null;
                };
                break;
            case "DATE":
                converter = (x) -> {
                    if(x==null){
                        return null;
                    }
                    if (x instanceof LocalDate)
                        return ((LocalDate) x).format(dateFormatter);
                    if (x instanceof String)
                        return LocalDate.parse((String) x).format(dateFormatter);

                    log.warn("[DateTimeConverter] unhandled column type({}), current type({})", columnType, x.getClass()
                            .getName());
                    return null;
                };
                break;
            case "DATETIME":
                converter = (x) -> {
                    if(x==null){
                        return null;
                    }
                    if (x instanceof LocalDateTime)// 增量更新阶段时间类型为LocalDateTime
                        return ((LocalDateTime) x).format(dateTimeFormatter);
                    if (x instanceof Timestamp)// 快照读取阶段时间类型为Timestamp
                        return ((Timestamp) x).toLocalDateTime().format(dateTimeFormatter);
                    if (x instanceof String)
                        return LocalDateTime.parse((String) x).format(dateTimeFormatter);

                    log.warn("[DateTimeConverter] unhandled column type({}), current type({})", columnType, x.getClass()
                            .getName());
                    return null;
                };
                break;
            case "TIMESTAMP":
                converter = (x) -> {
                    if(x==null){
                        return null;
                    }
                    if (x instanceof ZonedDateTime)// 增量更新阶段时间类型为ZonedDateTime, 需指定时区
                        return ((ZonedDateTime) x).withZoneSameInstant(timestampZoneId).format(timestampFormatter);
                    if (x instanceof Timestamp)// 快照读取阶段时间类型为Timestamp, 使用数据库服务器默认时区
                        return ((Timestamp) x).toLocalDateTime().format(timestampFormatter);
                    if (x instanceof String)
                        return ZonedDateTime.parse((String) x).withZoneSameInstant(timestampZoneId)
                                .format(timestampFormatter);

                    log.warn("[DateTimeConverter] unhandled column type({}), current type({})", columnType, x.getClass()
                            .getName());
                    return null;
                };
        }

        if (null != converter) {
            converterRegistration.register(SchemaBuilder.string(), converter);
            log.info("register converter for columnType {} to schema {}", columnType, SchemaBuilder.string().type());
        }
    }
}