package com.bill.controller;

import com.bill.dto.TodoListQueryResDto;
import com.bill.service.observe.ConcurrentMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ConcurrentMapController", description = "Local Cache 查詢")
@RestController
public class ConcurrentMapController {
    @Autowired
    private ConcurrentMapService service;

    @Operation(summary = "代辦事項查詢", description = "代辦事項查詢")
    @GetMapping("/map/todo/{seqNo}")
    public TodoListQueryResDto queryTodo(@PathVariable Integer seqNo) {
        return service.queryTodo(seqNo);
    }

    @Operation(summary = "代辦清單查詢", description = "代辦清單查詢")
    @GetMapping("/map/todo")
    public List<TodoListQueryResDto> queryTodoList() {
        return service.queryTodoList();
    }
}
