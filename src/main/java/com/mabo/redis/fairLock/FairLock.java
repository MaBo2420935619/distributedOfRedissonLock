package com.mabo.redis.fairLock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class FairLock {
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    private RedisTemplate redisTemplate;
    @ResponseBody
    @RequestMapping(value = "/fareLock",produces = "application/json")
    public String fareLock(){
        RLock fareLock = redissonClient.getFairLock("fareLock");
        //读之前加读锁，读锁的作用就是等待该lockkey释放写锁以后再读
        try {
            fareLock.lock();
        } finally {
            fareLock.unlock();
        }
        return "fareLock";
    }

}
