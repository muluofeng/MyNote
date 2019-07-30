package com.example.demo.RedissonLock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @author xiexingxing
 * @Created by 2019-07-28 17:10.
 */
public interface DistributedLocker {

    RLock lock(String lockKey);

    RLock lock(String lockKey, long timeout);

    RLock lock(String lockKey, TimeUnit unit, long timeout);

    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);

}