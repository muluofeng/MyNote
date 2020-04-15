package com.example.rabbitmq;

/**
 * @author xiexingxing
 * @Created by 2020-04-13 14:00.
 */

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send()   {
        String context = "hello " + new Date();
        System.out.println("发送消息 : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);    }

}