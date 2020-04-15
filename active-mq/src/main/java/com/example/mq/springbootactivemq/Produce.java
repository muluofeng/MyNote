package com.example.mq.springbootactivemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xiexingxing
 * @Created by 2019-08-27 15:46.
 */
@Service
public class Produce {
    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;

    public void sendTopicMessage() {
        ActiveMQTopic activeMQTopic = new ActiveMQTopic("springboot-activemq-topic");
        jmsMessagingTemplate.convertAndSend(activeMQTopic, new Date().toString());
    }


    public void sendQueueMessage() {
        ActiveMQQueue activeMQQueue = new ActiveMQQueue("springboot-activemq-queue");
        jmsMessagingTemplate.convertAndSend(activeMQQueue, new Date().toString());
    }
}
