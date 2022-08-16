package com.bill.service;

import com.bill.dto.*;

import java.util.List;

public interface ITodoListService {
    public void createTodoList(TodoListCreateReqDto reqDto);

    public void updateTodoList(TodoListUpdateReqDto reqDto);

    public void deleteTodoList(TodoListDeleteReqDto reqDto);
}
