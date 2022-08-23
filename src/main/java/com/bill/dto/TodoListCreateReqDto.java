package com.bill.dto;

import com.bill.entity.TodoList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoListCreateReqDto {
    @Schema(description = "todo", required = true, example = "運動")
    public String todo;

    public TodoList toEntity() {
        return new TodoList(this.todo, false);
    }
}
