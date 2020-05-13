package com.example.xing.aspect;

import com.alibaba.fastjson.JSONArray;
import com.example.xing.annotation.Resubmit;
import com.example.xing.config.RedisLockConfig;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.crypto.digest.MD5;

/**
 * @author xiexingxing
 * @Created by 2020-05-13 10:57.
 */
@Aspect
@Configuration
public class ReSubmitRedisLockAspect {
    @Autowired
    public ReSubmitRedisLockAspect(RedisLockConfig redisLockConfig) {
        this.redisLockConfig = redisLockConfig;
    }

    private final RedisLockConfig redisLockConfig;


    @Around("execution(public * *(..)) && @annotation(com.example.xing.annotation.Resubmit)")
    public Object interceptor(ProceedingJoinPoint pjp) throws IOException {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Resubmit lock = method.getAnnotation(Resubmit.class);
        int expire = lock.delaySeconds();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String bodyData = getBodyData(pjp);

        String lockKey = bodyData + "-" + request.getServletPath();

        String value = UUID.randomUUID().toString();
        try {
            // 假设上锁成功，但是设置过期时间失效，以后拿到的都是 false
            final boolean success = redisLockConfig.lock(lockKey, value, expire, TimeUnit.SECONDS);
            if (!success) {
                throw new RuntimeException("重复提交");
            }
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException("系统异常");
            }
        } finally {
        }
    }

    private String getBodyData(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        String json = JSONArray.toJSONString(args);
        return MD5.create().digestHex(json);
    }

    private String getBodyData(HttpServletRequest request) throws IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper( request);
        byte[] body = requestWrapper.getBody();
        return  new String(body, Charset.forName("UTF8"));
    }
}