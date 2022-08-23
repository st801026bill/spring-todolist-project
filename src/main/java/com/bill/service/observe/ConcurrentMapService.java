package com.bill.service.observe;

import com.bill.dto.TodoListQueryResDto;
import com.bill.entity.TodoList;
import com.bill.service.subject.H2Service;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ConcurrentMapService implements IObserveService, ICacheService {

    private static ConcurrentMap<Integer, TodoList> TODO_MAP = new ConcurrentHashMap<>();

    @Override
    public TodoListQueryResDto queryTodo(Integer seqNo) {
        TodoListQueryResDto result = new TodoListQueryResDto();
        TodoList todoList = MapUtils.getObject(TODO_MAP, seqNo);
        if(todoList == null) return null;

        BeanUtils.copyProperties(todoList, result);
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
    }
}
