package com.bill.service.subject;

import com.bill.dao.TodoListDao;
import com.bill.dto.TodoListCreateReqDto;
import com.bill.dto.TodoListDeleteReqDto;
import com.bill.dto.TodoListUpdateReqDto;
import com.bill.entity.TodoList;
import java.util.ArrayList;
import java.util.List;

import com.bill.service.observe.ConcurrentMapService;
import com.bill.service.observe.EhCacheService;
import com.bill.service.observe.IObserveService;
import com.bill.service.observe.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class H2Service implements IH2Service, ISubjectService {

    private static final List<IObserveService> cacheList = new ArrayList<>();

    @Autowired
    private TodoListDao todoListDao;
    @Autowired
    private H2Service h2Service;
    @Autowired
    private ConcurrentMapService concurrentMapService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private EhCacheService ehCacheService;

    @PostConstruct
    @DependsOn({"cacheManager", "ehCacheService"})  //Bean順序對了, 但還是無法載資料到ehcache
    public void updateCache() {
        h2Service.register(concurrentMapService);
        h2Service.register(redisService);
        h2Service.register(ehCacheService);
        h2Service.updateAllCache();
    }

    @Override
    public void createTodoList(TodoListCreateReqDto reqDto) {
        todoListDao.save(reqDto.toEntity());
        updateAllCache();
    }

    @Override
    public void updateTodoList(TodoListUpdateReqDto reqDto) {
        todoListDao.save(reqDto.toEntity());
        updateAllCache();
    }

    @Override
    public void deleteTodoList(TodoListDeleteReqDto reqDto) {
        todoListDao.delete(reqDto.toEntity());
        updateAllCache();
    }

    @Override
    public void register(IObserveService service) {
        cacheList.add(service);
    }

    @Override
    public void updateAllCache() {
        List<TodoList> todoLists = todoListDao.findAll();
        cacheList.forEach(cache -> {
            cache.updateCache(todoLists);
        });
    }
}
