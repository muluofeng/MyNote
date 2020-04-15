package com.example.xing;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiexingxing
 * @Created by 2018-12-09 10:01 PM.
 */
@RestController
public class DemoController {


    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;


    @GetMapping("/hello")
    String hello() {

        String pid = runtimeService.startProcessInstanceByKey("myProcess_1").getId();

        Task task = taskService.createTaskQuery().processInstanceId(pid).singleResult();
        return "hello world spring boot...." + task.getId();
    }
};