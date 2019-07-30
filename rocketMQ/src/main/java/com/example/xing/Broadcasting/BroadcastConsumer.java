package com.example.xing.Broadcasting;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @author xiexingxing
 * @Created by 2019-07-15 19:44.
 */
public class BroadcastConsumer {
    public static int num1 = 0;
    public static int num2 = 0;

    public static void main(String[] args) throws Exception {
        consumerTagA("TagA", "example_group_name1");
        consumerTagA("TagB", "example_group_name2");
    }


    private static void consumerTagA(String tag, String groupName) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        //指定 NameServer 地址
        consumer.setNamesrvAddr("localhost:9876");
        //设置 Consumer 第一次启动时从队列头部开始消费还是队列尾部开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        //set to broadcast mode
        consumer.setMessageModel(MessageModel.BROADCASTING);

        consumer.subscribe("TopicTest", tag);

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                if (tag.equals("TagA")) {
                    num1++;
                    System.out.printf(tag + "--" + num1 + " Receive New Messages: " + msgs + "%n");
                } else {
                    num2++;
                    System.out.printf(tag + "--" + num2 + " Receive New Messages: " + msgs + "%n");
                }


                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.printf("Broadcast Consumer Started.%n");


    }
}
