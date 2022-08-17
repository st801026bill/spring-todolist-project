package com.bill.config;

import com.bill.service.observe.ConcurrentMapService;
import com.bill.service.observe.EhCacheService;
import com.bill.service.observe.RedisService;
import com.bill.service.subject.H2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ObserveInitialRunner implements ApplicationRunner {

    @Autowired
    private H2Service h2Service;
    @Autowired
    private ConcurrentMapService concurrentMapService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private EhCacheService ehCacheService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        h2Service.register(concurrentMapService);
        h2Service.register(redisService);
        h2Service.register(ehCacheService);
        h2Service.updateAllCache();
    }
}
