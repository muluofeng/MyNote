package com.example.xing.demo;

import com.google.common.util.concurrent.RateLimiter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author xiexingxing
 * @Created by 2019-06-05 15:05.
 */
@RestController()
@RequestMapping("/api")
public class RushBuyController {

    private static final RateLimiter rateLimiter = RateLimiter.create(20);
    private static final Object o = new Object();
    private static int productCount = 35;

    /**
     * 我们模拟抢购商品，模拟我们服务器只能承受住20个并发，超过10个服务器会挂掉，
     * 我们用rateLimiter来进行限流，
     * 我们也没有传参数，当然实际场景复杂很多。
     *
     * @return 响应体
     */
    @RequestMapping(value = "/rushBuy2Product", produces = "application/json;charset=utf-8")
    public APIResponse rushBuy2Product() {

        //判断能否在1秒内得到令牌，如果不能则立即返回false，不会阻塞程序
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            System.out.println("短期无法获取令牌，真不幸，排队也瞎排--抢购失败");
            return null;
        }

//        System.out.println("进入抢购：等待时间为：" + rateLimiter.acquire());
        boolean b = productStock();
        if (b) {
            System.out.println("抢购成功");
        } else {
            System.out.println("抢购失败");
        }
        return new APIResponse<>().success();
    }

    /**
     * 抢购商品的库存
     *
     * @return
     */
    private boolean productStock() {
        if (productCount == 0) {
            return false;
        }
        synchronized (o) {
            if (productCount > 0) {
                --productCount;
                System.out.println("剩余库存为：" + productCount);
                return true;
            } else {
                return false;
            }
        }
    }
}