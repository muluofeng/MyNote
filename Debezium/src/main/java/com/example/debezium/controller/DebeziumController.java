package com.example.debezium.controller;

import com.example.debezium.config.DebeziumServerBootstrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiexingxing
 * @date 2023/3/26 8:48 PM
 */

@RestController
@RequestMapping("/debezium")
public class DebeziumController {

    @Autowired
    DebeziumServerBootstrap debeziumServerBootstrap;

    @RequestMapping("/addNewTable")
    public void   addNewTable(@RequestParam String dbName,@RequestParam  String tableName){
        debeziumServerBootstrap.restartWithNewTable(dbName,tableName);
    }
}
