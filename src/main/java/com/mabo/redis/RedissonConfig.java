package com.mabo.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedissonConfig {

    public RedissonConfig() {
    }

   /**
    * @Author mabo
    * @Description   获取redis连接对象
    */

    @Bean()
    public RedissonClient redisson() throws IOException {

//        //主从模式
//        Config config = new Config();
//        config.useMasterSlaveServers()
//                .setConnectTimeout(2000)
//                .setMasterAddress("redis://127.0.0.1:6379")
//                .addSlaveAddress("redis://127.0.0.1:6380")
//                .addSlaveAddress("redis://127.0.0.1:6381");
//        return  Redisson.create(config);

//        Config config=new Config();
//        //集群模式,集群节点的地址须使用“redis://”前缀，否则将会报错。
//        //此例集群为3节点，各节点1主1从
//        config.useClusterServers().addNodeAddress("rediss://127.0.0.1:6379",
//                "rediss://127.0.0.1:6380",
//                "rediss://127.0.0.1:6381");
//        return Redisson.create(config);


        //集群模式,需要开启redis集群配置 cluster-enable yes
//
//        Config config = new Config();
//        config.useClusterServers()
//                .setScanInterval(2000) // cluster state scan interval in milliseconds
//                .addNodeAddress("redis://127.0.0.1:6379")
//                .addNodeAddress("redis://127.0.0.1:6380");
//        RedissonClient redisson = Redisson.create(config);
//        return redisson;

//         1.创建配置
        Config config = new Config();
//         2.根据 Config 创建出 RedissonClient 示例。
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }






}
