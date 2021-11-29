package com.mabo.redis.semaphore;

import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SemaphoreLock {

    @Autowired
    RedissonClient redissonClient;
    @Autowired
    RedisTemplate redisTemplate;
    @ResponseBody
    @RequestMapping(value = "/addSemaphore",produces = "application/json")
    public String addSemaphore(HttpServletRequest request) throws InterruptedException {
        Object semaphore1 = redisTemplate.boundValueOps("semaphore").get();
        if (semaphore1==null){
            redisTemplate.boundValueOps("semaphore").set(2);
        }
        RSemaphore semaphore = redissonClient.getSemaphore("semaphore");
        semaphore.acquire();
        return semaphore1.toString();
    }
    @RequestMapping(value = "/decSetSemaphore",produces = "application/json")
    public String decSetSemaphore(HttpServletRequest request) throws InterruptedException {
        Object semaphore1 = redisTemplate.boundValueOps("semaphore").get();
        RSemaphore semaphore = redissonClient.getSemaphore("semaphore");
        semaphore.release();
        return "decSetSemaphore";
    }

}
