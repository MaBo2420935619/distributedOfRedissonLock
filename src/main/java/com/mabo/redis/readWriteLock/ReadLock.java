package com.mabo.redis.readWriteLock;

import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReadLock {
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    private RedisTemplate redisTemplate;
    @ResponseBody
    @RequestMapping(value = "/read",produces = "application/json")
    public String read(){
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("readWriteLock");
        //读之前加读锁，读锁的作用就是等待该lockkey释放写锁以后再读
        RLock rLock = readWriteLock.readLock();
        try {
            rLock.lock();
            Object o = redisTemplate.boundValueOps("1").get();
            int  a = (int)o;
            System.out.println(a);
//            TimeUnit.SECONDS.sleep(2);
            return  String.valueOf(a);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            rLock.unlock();
        }
    }

}
