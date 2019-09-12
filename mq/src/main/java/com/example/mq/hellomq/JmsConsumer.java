package com.example.mq.hellomq;

import org.apache.activemq.ActiveMQConnectionFactory;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author xiexingxing
 * @Created by 2019-08-26 13:46.
 */
public class JmsConsumer {
//    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:32768";

    public static final String ACTIVEMQ_URL = "tcp://192.168.2.109:61616";


    public static final String QUEUE_NAME = "MY_QUEUE_NAME";

    public static void main(String[] args) throws JMSException, IOException {

        mqtest("1");

    }

    public static void mqtest(String id) throws JMSException, IOException {

        //1. 创建连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin", ACTIVEMQ_URL);

        //2. 获取连接
        Connection connection = factory.createConnection();
        connection.start();

        //3. 创建session   boolean transacted, int acknowledgeMode
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地  queue 还是topic
        Queue queue = session.createQueue(QUEUE_NAME);

        //5. 创建消息的消费者
        MessageConsumer consumer = session.createConsumer(queue);

        /*
         *//**
         * 1. 一直循环接受
         *//*
        while (true) {
            //6. 接受消息
            TextMessage textMessage = (TextMessage) consumer.receive(); //阻塞的方法
//            TextMessage textMessage = (TextMessage) consumer.receive(4000L); //超过时间就不阻塞了
            if (textMessage == null) {
                break;
            } else {
                System.out.println(textMessage.getText());
            }
        }*/


        /**
         * 通说监听的方式
         */
        consumer.setMessageListener(message -> {
            try {
                System.out.println(id+"--"+((TextMessage) message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

//
        System.in.read(); //？？？
        consumer.close();
        session.close();
        connection.close();

        System.out.println("mq 消息消费完成2");
    }
}
