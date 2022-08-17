package com.bill.service;

import com.bill.dto.*;

import java.util.List;

public interface ITodoListService {
    void createTodoList(TodoListCreateReqDto reqDto);

    void updateTodoList(TodoListUpdateReqDto reqDto);

    void deleteTodoList(TodoListDeleteReqDto reqDto);
}
