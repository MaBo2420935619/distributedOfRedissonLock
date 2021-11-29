package com.mabo.redis.redissonRedLock;

import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@Controller
public class RedissonRedLockTest {
    @Autowired
    RedissonClient redissonClient;

    @ResponseBody    //连续在浏览器输入三个当前路径，就可以发现第三个执行需要15秒，加锁成功
    @RequestMapping(value = "/redLock",produces = "application/json")
    public String redLock() {
        RLock lock1 = redissonClient.getLock("lock1");
        RLock lock2 = redissonClient.getLock("lock2");
        RLock lock3 = redissonClient.getLock("lock3");
        RedissonRedLock redLock=new RedissonRedLock(lock1,lock2,lock3);
        try {
            redLock.tryLock(10, TimeUnit.SECONDS);
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            redLock.unlock();
        }
        return "redLock";
    }
}
