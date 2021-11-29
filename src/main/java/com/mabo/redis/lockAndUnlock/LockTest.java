package com.mabo.redis.lockAndUnlock;

import com.mabo.redis.RedissonConfig;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Controller
public class LockTest {
    static int  a=0;
    @Autowired
    RedissonClient redissonClient;

    @ResponseBody
    @RequestMapping(value = "/hello",produces = "application/json")
    public String hello(HttpServletRequest request) throws IOException, InterruptedException {
        RLock lock = redissonClient.getLock("lock");
        try {
            // 2.加锁
            boolean res = lock.tryLock((long)30, (long)10, TimeUnit.SECONDS);
            if (res) {
                //成功获得锁，在这里处理业务
                a++;
                System.out.println(a);
            }
        } catch (Exception e) {
            //TODO
        } finally {
            lock.unlock();
        }
        return "test lock ok";
    }
}
