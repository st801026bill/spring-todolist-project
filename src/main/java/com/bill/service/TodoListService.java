package com.bill.service;

import com.bill.dto.TodoListCreateReqDto;
import com.bill.dto.TodoListDeleteReqDto;
import com.bill.dto.TodoListUpdateReqDto;

import com.bill.service.subject.H2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoListService implements ITodoListService {

    @Autowired
    private H2Service h2Service;

    @Override
    public void createTodoList(TodoListCreateReqDto reqDto) { h2Service.createTodoList(reqDto); }

    @Override
    public void updateTodoList(TodoListUpdateReqDto reqDto) {
        h2Service.updateTodoList(reqDto);
    }

    @Override
    public void deleteTodoList(TodoListDeleteReqDto reqDto) {
        h2Service.deleteTodoList(reqDto);
    }
}
