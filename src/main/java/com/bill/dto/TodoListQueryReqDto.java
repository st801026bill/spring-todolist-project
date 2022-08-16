package com.bill.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class TodoListQueryReqDto {
    @Schema(description = "page", required = true, example = "1")
    @JsonProperty("Page")
    private int page;

    @Schema(description = "size", required = true, example = "10")
    @JsonProperty("Size")
    private int size;
}
