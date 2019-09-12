package com.example.mq.hellomq.presistence;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 *  持久话 topic  -- 生产者
 * @author xiexingxing
 * @Created by 2019-08-26 11:53.
 */
public class JmsProduce_topic_presistence {
//    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:32768";
    public static final String ACTIVEMQ_URL = "tcp://192.168.2.109:61616";

    public static final String TOPIC_NAME = "TOPIC_NAME_PRESISTENCE";

    public static void main(String[] args) throws JMSException {
        //1. 创建连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin", ACTIVEMQ_URL);

        //2. 获取连接
        Connection connection = factory.createConnection();


        //3. 创建session   boolean transacted, int acknowledgeMode
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地  queue 还是topic
        Topic topic = session.createTopic(TOPIC_NAME);

        //5. 创建消息的生产者
        MessageProducer producer = session.createProducer(topic);

        /**
         * 设置持久化topic 在启动
         */
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();


        //6. 生产消息发送到queue
        for (int i = 1; i < 10; i++) {
            try {
                //7. 创建消息
                TextMessage msg = session.createTextMessage("msg---" + i);
                //8. 发送消息
                producer.send(msg);

            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        producer.close();
        session.close();
        connection.close();

        System.out.println("mq 消息发布完成");
    }
}
