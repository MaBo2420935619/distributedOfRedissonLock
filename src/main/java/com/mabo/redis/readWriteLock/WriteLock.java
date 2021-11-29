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
public class WriteLock {
    @Autowired
    RedissonClient redissonClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @ResponseBody
    @RequestMapping(value = "/write",produces = "application/json")
    public String read(){
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("readWriteLock");
        //读之前加读锁，读锁的作用就是等待该lockkey释放写锁以后再读
        RLock wLock = readWriteLock.writeLock();
        try {
            int  a=0;
            wLock.lock();
            Object o = redisTemplate.boundValueOps("1").get();
            if (o==null){
                redisTemplate.boundValueOps("1").set(0);
            }
            else {
                a = (int)o;
                a++;
                redisTemplate.boundValueOps("1").set(a);
            }
//            TimeUnit.SECONDS.sleep(10);
            return String.valueOf(a);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            wLock.unlock();
        }

    }
}
