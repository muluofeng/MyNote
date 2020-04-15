package com.example.mq.hellomq.presistence;

import org.apache.activemq.ActiveMQConnectionFactory;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

/**
 * 持久话 topic  -- 消费者
 * <p>
 * - topic 持久化，默认不是持久化的
 * 消费者只要订阅了一次topic，等于向mq注册，然后运行生产者，不论 消费者是否在线都会接收到，不在线的话，下次连接的时候会把没有收到的消息接受下来
 *
 * @author xiexingxing
 * @Created by 2019-08-26 13:46.
 */
public class JmsConsumer_topic_presistence {
    //    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:32768";
    public static final String ACTIVEMQ_URL = "tcp://192.168.2.109:61616";

    public static final String TOPIC_NAME = "TOPIC_NAME_PRESISTENCE";

    public static void main(String[] args) throws JMSException, IOException {

        mqtest("Consumer1");

    }

    public static void mqtest(String id) throws JMSException, IOException {

        //1. 创建连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin", ACTIVEMQ_URL);

        //2. 获取连接
        Connection connection = factory.createConnection();
        connection.setClientID("ClientID");

        //3. 创建session   boolean transacted, int acknowledgeMode
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地  queue 还是topic
        Topic topic = session.createTopic(TOPIC_NAME);

        //创建持久的订阅者
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remake。。。");
        // 5 发布订阅
        connection.start();


        Message message = topicSubscriber.receive();// 一直等
        while (null != message) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println(" 收到的持久化 topic ：" + textMessage.getText());
            message = topicSubscriber.receive(3000L);    // 等1秒后meesage 为空，跳出循环，控制台关闭
        }

        System.in.read(); //？？？
        topicSubscriber.close();
        session.close();
        connection.close();

        System.out.println("mq 消息消费完成2");
    }
}
