package com.example.mq.hellomq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author xiexingxing
 * @Created by 2019-08-26 11:53.
 */
public class JmsProduce {
//    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:32768";
//    public static final String ACTIVEMQ_URL = "tcp://192.168.2.109:61617";

    public static final String ACTIVEMQ_URL = "tcp://192.168.2.109:61616";

    //集群配置
//    public static final String ACTIVEMQ_URL = "failover:(tcp://192.168.2.109:61616,tcp://192.168.2.109:61617,tcp://192.168.2.109:61618)?randomize=false";

    public static final String QUEUE_NAME = "MY_QUEUE_NAME";

    public static void main(String[] args) throws JMSException {
        //1. 创建连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin", ACTIVEMQ_URL);

        //2. 获取连接
        Connection connection = factory.createConnection();
        connection.start();

        //3. 创建session   boolean transacted （事务）, int acknowledgeMode （签收）
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地  queue 还是topic
        Queue queue = session.createQueue(QUEUE_NAME);

        //5. 创建消息的生产者

        //5.1  不使用异步回调通知的方式
//        MessageProducer producer = session.createProducer(queue);


        //5.2 使用 异步回调通知的方式
        ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session.createProducer(queue);

        //设置持久化
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        //6. 生产消息发送到queue
        for (int i = 1; i < 10; i++) {
            try {
                //7. 创建消息
                TextMessage msg = session.createTextMessage("msg---" + i);
                //8  发送消息
                //8.1  不使用异步回调通知的方式，直接发送消息
//                producer.send(msg);
                //8.2  使用异步回调通知的方式，直接发送消息
                producer.send(msg, new AsyncCallback() {
                    @Override
                    public void onSuccess() {
                        System.out.println("异步投递成功");
                    }

                    @Override
                    public void onException(JMSException exception) {
                        try {
                            System.out.println("异步投递失败" + msg.getJMSMessageID());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }

                    }
                });

            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        try {
            System.in.read(); //？？？
        } catch (IOException e) {
            e.printStackTrace();
        }
        producer.close();
        session.close();
        connection.close();

        System.out.println("mq 消息发布完成");
    }
}
