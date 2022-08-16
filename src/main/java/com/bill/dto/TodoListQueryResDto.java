package com.bill.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoListQueryResDto {
    private Integer seqNo;

    private String todo;

    private Boolean isDone;

    private LocalDateTime createDateTime;

    private LocalDateTime updateDateTime;
}
