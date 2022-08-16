package com.bill.service.subject;

import com.bill.dto.TodoListCreateReqDto;
import com.bill.dto.TodoListDeleteReqDto;
import com.bill.dto.TodoListUpdateReqDto;
import com.bill.entity.TodoList;

import java.util.List;

public interface IH2Service {
    void createTodoList(TodoListCreateReqDto reqDto);
    void updateTodoList(TodoListUpdateReqDto reqDto);
    void deleteTodoList(TodoListDeleteReqDto todoList);
}
