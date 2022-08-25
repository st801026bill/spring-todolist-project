package com.bill.dto;

import com.bill.entity.TodoList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoListDeleteReqDto {
    @Schema(description = "seqNo", required = true, example = "1")
    public Integer seqNo;

    public TodoList toEntity() {
        return new TodoList(this.seqNo);
    }
}
