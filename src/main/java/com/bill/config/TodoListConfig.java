package com.bill.config;

import com.bill.service.observe.ConcurrentMapService;
import com.bill.service.observe.EhCacheService;
import com.bill.service.observe.RedisService;
import com.bill.service.subject.H2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Configuration
public class TodoListConfig {
//    @Autowired
//    private H2Service h2Service;
//
//    @Autowired
//    private ConcurrentMapService concurrentMapService;
//    @Autowired
//    private RedisService redisService;
//    @Autowired
//    private EhCacheService ehCacheService;
//
//    @Bean
//    @DependsOn("cacheManager")
//    public void updateCache() {
//        h2Service.register(concurrentMapService);
//        h2Service.register(redisService);
//        h2Service.register(ehCacheService);
//        h2Service.updateAllCache();
//    }
}
