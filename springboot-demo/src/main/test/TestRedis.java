import com.example.demo.DemoApplication;
import com.example.demo.service.AccountServiceTestCache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xiexingxing
 * @Created by 2019-06-17 12:11.
 */
@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedis {

    @Autowired
    AccountServiceTestCache accountService;
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void testfindOne() {
//        Object data = accountService.findAll();
        Object data = accountService.findById(1);
        System.out.println(data);
    }

    @Test
    public void testUpdate() {
//        Object data = accountService.findAll();
        Object data = accountService.updateById(1, "update");
        System.out.println(data);
    }

    @Test
    public void testDelete() {
//        Object data = accountService.findAll();
        accountService.deleteById(2);
    }

    /**
     * 测试redis 存储常用的数据结构
     */

    @Test
    public void testRedis() {
        //1. 存储set
        Set<String> set1 = new HashSet<String>();
        set1.add("set1");
        set1.add("set2");
        set1.add("set3");
        redisTemplate.opsForSet().add("set1", set1);
        Set<String> resultSet = redisTemplate.opsForSet().members("set1");
        System.out.println("resultSet:" + resultSet);

        //2.存储hashmap
        HashMap<String, String> map = new HashMap<>();
        map.put("A", "aaaaa");
        map.put("B", "bbbbb");
        map.put("C", "ccccc");
        redisTemplate.opsForHash().putAll("map", map);
        Map map1 = redisTemplate.opsForHash().entries("map");
        System.out.println("resultMap" + map1);

        //3.存储list
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("v");
        redisTemplate.opsForList().leftPush("list1", list);
        System.out.println(redisTemplate.opsForList().size("list1"));
        System.out.println("------");

        //4. 存储字符串
        String s = "测试数据";
        redisTemplate.opsForValue().set("name",s);
        System.out.println(redisTemplate.opsForValue().get("name"));

        //5.zset


//        List<String> leftPopList = (List<String>) redisTemplate.opsForList().leftPop("list1");
//        List<String> rightPopList = (List<String>) redisTemplate.opsForList().rightPop("list1");
//        System.out.println("resultlist" + leftPopList);
//        System.out.println("resultlist" + rightPopList);
    }

}
