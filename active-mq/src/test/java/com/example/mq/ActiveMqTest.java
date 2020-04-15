package com.example.mq;

import com.example.mq.springbootactivemq.Produce;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author xiexingxing
 * @Created by 2019-08-27 15:54.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ActiveMqTest {

    @Autowired
    Produce produce;

    @Test
    public void testProduceTopic() {
        produce.sendTopicMessage();
    }


    @Test
    public void testProduceQueue() {
        produce.sendQueueMessage();
    }
}

