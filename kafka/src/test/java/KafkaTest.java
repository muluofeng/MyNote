import com.example.kafka.Application;
import com.example.kafka.Consumer;
import com.example.kafka.Producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiexingxing
 * @Created by 2019-10-09 13:55.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class KafkaTest {

    @Autowired
    Producer producer;


    @Autowired
    Consumer consumer;


    @Test
    public void testProducer() {
        producer.sendChannelMess("test13","测试发送数据");

    }


    @Test
    public void testConsumer() {

    }
}