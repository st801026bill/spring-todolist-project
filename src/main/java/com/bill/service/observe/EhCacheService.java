package com.bill.service.observe;

import com.bill.dto.TodoListQueryResDto;
import com.bill.entity.TodoList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EhCacheService implements IObserveService, ICacheService {

    @Autowired
    private EhCacheService service;

    private static ConcurrentMap<Integer, TodoList> TODO_MAP = new ConcurrentHashMap<>();

    @Override
    public TodoListQueryResDto queryTodo(Integer seqNo) {
        TodoListQueryResDto result = new TodoListQueryResDto();
        TodoList todo = service.query(seqNo);
        BeanUtils.copyProperties(todo, result);
        return result;
    }

    @Override
    public List<TodoListQueryResDto> queryTodoList() {
        return new ArrayList<>(TODO_MAP.values()).stream().map(todo -> {
            TodoListQueryResDto resDto = new TodoListQueryResDto();
            BeanUtils.copyProperties(todo, resDto);
            return resDto;
        }).collect(Collectors.toList());
    }

    @Override
    public void updateCache(List<TodoList> todoList) {
        TODO_MAP = todoList.stream().collect(Collectors.toConcurrentMap(TodoList::getSeqNo, Function.identity()));
        todoList.forEach(todo -> service.query(todo.getSeqNo()));
    }

    @Cacheable(value="TodoListCache", key = "#seqNo")
    public TodoList query(Integer seqNo) {
        log.info("todo: {}", seqNo);
        return MapUtils.getObject(TODO_MAP, seqNo);
    }
}
