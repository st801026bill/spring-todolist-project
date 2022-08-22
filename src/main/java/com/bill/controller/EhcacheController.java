package com.bill.controller;

import com.bill.dto.TodoListQueryResDto;
import com.bill.entity.TodoList;
import com.bill.service.observe.ConcurrentMapService;
import com.bill.service.observe.EhCacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "EhcacheController", description = "EhCache 查詢")
@RestController
public class EhcacheController {
    @Autowired
    private EhCacheService service;

    @Operation(summary = "代辦事項查詢", description = "代辦事項查詢")
    @GetMapping("/ehcache/{seqNo}")
    public TodoListQueryResDto queryTodo(@PathVariable Integer seqNo) {
        return service.queryTodo(seqNo);
    }

    @Operation(summary = "代辦清單查詢", description = "代辦清單查詢")
    @GetMapping("/ehcache")
    public List<TodoListQueryResDto> queryTodoList() {
        return service.queryTodoList();
    }
}
