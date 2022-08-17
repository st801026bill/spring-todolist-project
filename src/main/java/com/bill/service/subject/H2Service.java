package com.bill.service.subject;

import com.bill.dao.TodoListDao;
import com.bill.dto.TodoListCreateReqDto;
import com.bill.dto.TodoListDeleteReqDto;
import com.bill.dto.TodoListUpdateReqDto;
import com.bill.entity.TodoList;
import java.util.ArrayList;
import java.util.List;

import com.bill.service.observe.IObserveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class H2Service implements IH2Service, ISubjectService {

    private static final List<IObserveService> cacheList = new ArrayList<>();

    @Autowired
    private TodoListDao todoListDao;

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
