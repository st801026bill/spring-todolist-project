package com.bill.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TodoListQueryResDto {
    private Integer seqNo;

    private String todo;

    private Boolean isDone;

    private LocalDateTime createDateTime;

    private LocalDateTime updateDateTime;
}
