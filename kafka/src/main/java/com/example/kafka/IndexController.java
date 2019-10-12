package com.example.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiexingxing
 * @Created by 2019-10-09 14:01.
 */
@RequestMapping("/kafka")
@Controller
public class IndexController {

    @Autowired
    Producer producer;


    @Autowired
    Consumer consumer;


    @RequestMapping("/test/{topicid}")
    public void index(@PathVariable String topicid) {
        producer.sendChannelMess(topicid, "测试发送数据");
    }
}