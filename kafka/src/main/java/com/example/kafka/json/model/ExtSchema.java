package com.example.kafka.json.model;

import java.util.List;

import lombok.Data;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-04-23 19:54
 */
@Data
public class ExtSchema {

    /**
     * @return the type of this schema
     */
//    private org.apache.kafka.connect.data.ExtSchema.Type type;

    private String type;

    private boolean optional;

    private String name;

    private List<ExtField> fields;

}
