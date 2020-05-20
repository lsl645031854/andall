package com.andall.sally.supply.config;

import com.alibaba.fastjson.JSON;
import com.andall.sally.supply.utils.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@ConditionalOnBean(RedissonProperties.class)
public class RedissonAutoConfig {

    @Autowired
    private RedissonProperties redissonProperties;

    @Bean
    public RedissonClient initRedissonBean(){
        Config config = createSingleServerConfig();
        RedissonClient client = Redisson.create(config);
        log.info("init RedissonClient to RedissonUtils successful!"+ JSON.toJSONString(client.getNodesGroup()));
        RedissonUtils.setRedissonClient(client);
        return client;
    }

    /**
     * 创建单例redisson服务配置
     * @return
     */
    private Config createSingleServerConfig(){
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://".concat(redissonProperties.getAddress()).concat(":").concat(redissonProperties.getPort().toString()));
        if(redissonProperties.getDb() != null){
            singleServerConfig.setDatabase(redissonProperties.getDb());
        }
        if(StringUtils.isNotEmpty(redissonProperties.getPassword())){
            singleServerConfig.setPassword(redissonProperties.getPassword());
        }
        if(redissonProperties.getTimeout() != null){
            singleServerConfig.setTimeout(redissonProperties.getTimeout());
        }else{
            singleServerConfig.setTimeout(200);
        }
        return config;
    }

}
