import com.example.demo.Application;
import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
import com.example.demo.service.DemoService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author xiexingxing
 * @Created by 2019-06-17 12:11.
 */
@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUserService {

    @Autowired
    AccountService accountService;
    @Autowired
    DemoService demoService;

    @Test
    public void testUserService() throws Exception {
        Account account = new Account();
        account.setName("bbb");
        accountService.addAccountWithNoRollbackForException(account);

    }

    /**
     * 测试service 一个方法调用另一个方法
     *
     * @throws Exception
     */
    @Test
    public void testUserService2() throws Exception {
        accountService.methodA();

    }

    /**
     * 测试其他service  调用  accountservice   的方法
     *
     * @throws Exception
     */
    @Test
    public void testDemeservice() {
        try {
            demoService.testAccount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 测试 testMantatory
     *
     * @throws Exception
     */
    @Test
    public void testMantatory() {
        try {
            demoService.testMantatory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试 testAddNEVER
     *
     * @throws Exception
     */
    @Test
    public void testAddNEVER() {
        try {
            demoService.testAddNEVER();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
