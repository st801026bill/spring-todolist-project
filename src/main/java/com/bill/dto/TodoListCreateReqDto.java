package com.bill.dto;

import com.bill.entity.TodoList;
import io.swagger.v3.oas.annotations.media.Schema;

public class TodoListCreateReqDto {
    @Schema(description = "todo", required = true, example = "運動")
    public String todo;

    public TodoList toEntity() {
        return new TodoList(this.todo);
    }
}
