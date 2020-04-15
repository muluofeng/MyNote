package com.example.xing.Broadcasting;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author xiexingxing
 * @Created by 2019-07-15 19:42.
 */
public class BroadcastProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("localhost:9876");

        producer.start();

        for (int i = 0; i < 100; i++) {
            String Tag = i % 2 == 0 ? "TagA" : "TagB";
            Message msg = new Message("TopicTest",
                    Tag,
                    "OrderID" + i,
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }
}
