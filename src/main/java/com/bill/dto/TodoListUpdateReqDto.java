package com.bill.dto;

import com.bill.entity.TodoList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoListUpdateReqDto {
    @Schema(description = "seqNo", required = true, example = "1")
    public Integer seqNo;
    @Schema(description = "todo", required = true, example = "運動")
    public String todo;
    @Schema(description = "isDone", required = true, example = "是否完成")
    public Boolean isDone;

    public TodoList toEntity() {
        return new TodoList(this.seqNo, this.todo, this.isDone);
    }
}
