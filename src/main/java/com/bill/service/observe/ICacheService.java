package com.bill.service.observe;

import com.bill.dto.TodoListQueryResDto;

import java.util.List;

public interface ICacheService {
    TodoListQueryResDto queryTodo(Integer seqNo);
    List<TodoListQueryResDto> queryTodoList();
}
