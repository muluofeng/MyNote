package com.example.xing.simple;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiexingxing
 * @Created by 2018-12-09 8:58 PM.
 */
public class Producer {
    public static final int NUM = 100;
    public static volatile int sendNums = 0;
    static DefaultMQProducer producer;

    static CountDownLatch countDownLatch = new CountDownLatch(NUM);

    static {
        //创建一个消息生产者，并设置一个消息生产者组
        producer = new DefaultMQProducer("niwei_producer_group");

        //指定 NameServer 地址
        producer.setNamesrvAddr("localhost:9876");

        //初始化 Producer，整个应用生命周期内只需要初始化一次
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {


        ExecutorService executorService = Executors.newFixedThreadPool(10);


        for (int i = 0; i < NUM; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    //创建一条消息对象，指定其主题、标签和消息内容
                    Message msg = null;
                    try {

                        msg = new Message(
                                "topic_example_java" /* 消息主题名 */,
                                "TagA" /* 消息标签 */,
                                ("Hello Java demo RocketMQ " + sendNums).getBytes(RemotingHelper.DEFAULT_CHARSET) /* 消息内容 */
                        );
                        //发送消息并返回结果
                        SendResult sendResult = producer.send(msg);

                        System.out.printf("%s%n", sendResult);

                        sendNums++;

                        countDownLatch.countDown();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (MQClientException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (RemotingException e) {
                        e.printStackTrace();
                    } catch (MQBrokerException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        // 一旦生产者实例不再被使用则将其关闭，包括清理资源，关闭网络连接等
        countDownLatch.await();
        producer.shutdown();
    }


}
