package com.bill.controller;

import com.bill.SpringTodolistProjectApplication;
import com.bill.dto.TodoListQueryResDto;
import com.bill.entity.TodoList;
import com.bill.service.observe.ConcurrentMapService;
import com.bill.util.JsonUtils;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringTodolistProjectApplication.class)
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@Slf4j
public class ConcurrentMapControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonUtils jsonUtils;

    @MockBean
    private ConcurrentMapService service;

    @Test
    @Order(1)
    void queryTodoEqual() throws Exception {
        String responseJson = jsonUtils.getInput("queryTodoResponse.json");
        TodoListQueryResDto expectTodo = jsonUtils.readValue(responseJson, TodoListQueryResDto.class);
        Mockito.when(service.queryTodo(1)).thenReturn(expectTodo);

        String result = mockMvc.perform(get("/map/todo/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        TodoListQueryResDto resDto = jsonUtils.readValue(result, TodoListQueryResDto.class);
        log.info("expectTodo: {}", expectTodo);
        log.info("resDto: {}", resDto);
        assertEquals(expectTodo, resDto);
    }

    @Test
    @Order(2)
    void queryTodoNotEqual() throws Exception {
        String responseJson = jsonUtils.getInput("queryTodoResponse.json");
        TodoListQueryResDto expectTodo = jsonUtils.readValue(responseJson, TodoListQueryResDto.class);
        Mockito.when(service.queryTodo(1)).thenReturn(expectTodo);

        String result = mockMvc.perform(get("/map/todo/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        TodoListQueryResDto resDto = jsonUtils.readValue(result, TodoListQueryResDto.class);

        log.info("expectTodo: {}", expectTodo);
        log.info("resDto: {}", resDto);
        resDto.setIsDone(true);
        assertNotEquals(expectTodo, resDto);
    }

//    @Test
//    @Order(3)
//    void queryTodoList() throws Exception {
//        String responseJson = jsonUtils.getInput("queryTodoListResponse.json");
//        List<TodoListQueryResDto> expectTodoList = jsonUtils.readValue(responseJson, new TypeReference<List<TodoListQueryResDto>>(){});
//        Mockito.when(service.queryTodoList()).thenReturn(expectTodoList);
//
//        String result = mockMvc.perform(get("/map/todo/").accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
//
//        List<TodoListQueryResDto> resDto
//    }

    private ConcurrentMap<Integer, TodoList> generateLocalcahce(TodoList... todoList) {
        ConcurrentMap<Integer, TodoList> todoMap = new ConcurrentHashMap<>();
        for(int i=0;i<todoList.length; i++) {
            todoMap.put(i+1, todoList[i]);
        }
        return todoMap;
    }

    private TodoList todo1() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime dttm = LocalDateTime.parse("2022-08-10 17:43:12.909428", formatter);
        return new TodoList(1, "運動", false, dttm, dttm);
    }

    private TodoList todo2() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime dttm = LocalDateTime.parse("2022-08-10 17:43:12.909428", formatter);
        return new TodoList(2, "看書", true, dttm, dttm);
    }
}
