package com.example.xing.simple;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author xiexingxing
 * @Created by 2018-12-09 9:00 PM.
 */
public class Consumer {
    static int sendNums = 0;

    public static void main(String[] args) throws Exception {

        //创建一个消息消费者，并设置一个消息消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("niwei_consumer_group");
        //指定 NameServer 地址
        consumer.setNamesrvAddr("localhost:9876");
        //设置 Consumer 第一次启动时从队列头部开始消费还是队列尾部开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //订阅指定 Topic 下的所有消息
        consumer.subscribe("topic_example_java", "*");

        Set set = new HashSet<String>();
        //注册消息监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                //默认 list 里只有一条消息，可以通过设置参数来批量接收消息
                if (list != null) {
                    for (MessageExt ext : list) {
                        sendNums++;
                        try {
                            String msgId = ext.getMsgId();
                            if (set.contains(msgId)) {
                                System.out.println("消息重复--" + msgId);
                            } else {
                                set.add(msgId);
                            }
                            System.out.println(msgId + "--" + new String(ext.getBody(), "UTF-8") + "---"+sendNums);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 消费者对象在使用之前必须要调用 start 初始化
        consumer.start();
        System.out.println("消息消费者已启动");
        TimeUnit.SECONDS.sleep(20);
        System.out.println("消费总的次数：" + sendNums);
    }
}