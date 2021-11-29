package com.mabo.redis.semaphore;

import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;
/**
 * @Author mabo
 * @Description   不推荐使用，本人测试了很多次，都会出现线程死锁的情况
 */
@Controller
public class RPermitExpirableSemaphoreTest {

    @Autowired
    RedissonClient redissonClient;
    @Autowired
    RedisTemplate redisTemplate;
    @ResponseBody
    @RequestMapping(value = "/addSemaphoreTime",produces = "application/json")
    public String addSemaphore(HttpServletRequest request) throws InterruptedException {
        System.out.println("111");
        RPermitExpirableSemaphore semaphore = redissonClient.getPermitExpirableSemaphore("semaphoreTime111");
//        String permitId = semaphore.acquire();
        // 获取一个信号，有效期只有3秒钟。
        String permitId = semaphore.acquire(3, TimeUnit.SECONDS);
        // ...
        semaphore.release(permitId);
        return "addSemaphoreTime";
    }

}
