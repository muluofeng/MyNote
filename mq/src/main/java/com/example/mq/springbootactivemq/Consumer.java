package com.example.mq.springbootactivemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author xiexingxing
 * @Created by 2019-08-27 15:49.
 */
@Service
public class Consumer {

    @JmsListener(destination = "springboot-activemq-topic",containerFactory ="topicListenerFactory")
    public void acceptTopic(TextMessage textMessage) throws JMSException {
        String text = textMessage.getText();
        System.out.println("topic:获取mq消息" + text);
    }

    @JmsListener(destination = "springboot-activemq-queue",containerFactory = "queueListenerFactory")
    public void acceptQueue(TextMessage textMessage) throws JMSException {
        String text = textMessage.getText();
        System.out.println("queue:获取mq消息" + text);
    }

}
