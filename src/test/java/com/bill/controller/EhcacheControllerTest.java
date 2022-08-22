package com.bill.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bill.SpringTodolistProjectApplication;
import com.bill.dto.TodoListQueryResDto;
import com.bill.entity.TodoList;
import com.bill.service.observe.EhCacheService;
import com.bill.service.observe.RedisService;
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

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringTodolistProjectApplication.class)
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@Slf4j
public class EhcacheControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonUtils jsonUtils;

    @MockBean
    private EhCacheService service;

    @Test
    @Order(1)
    void queryTodoEqual() throws Exception {
        TodoListQueryResDto expectTodo = initExceptTodoTest();
        TodoListQueryResDto resDto = initQueryTodoTest();
        log.info("resDto: {}", resDto);
        assertEquals(expectTodo, resDto);
    }

    @Test
    @Order(2)
    void queryTodoNotEqual() throws Exception {
        TodoListQueryResDto expectTodo = initExceptTodoTest();
        TodoListQueryResDto resDto = initQueryTodoTest();
        log.info("resDto: {}", resDto);
        resDto.setIsDone(true);
        assertNotEquals(expectTodo, resDto);
    }

    private TodoListQueryResDto initExceptTodoTest() throws Exception {
        String responseJson = jsonUtils.getInput("queryTodoResponse.json");
        TodoListQueryResDto expectTodo = jsonUtils.readValue(responseJson, TodoListQueryResDto.class);
        Mockito.when(service.queryTodo(1)).thenReturn(expectTodo);

        log.info("expectTodo: {}", expectTodo);
        return expectTodo;
    }

    private TodoListQueryResDto initQueryTodoTest() throws Exception {
        String result = mockMvc.perform(get("/ehcache/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return jsonUtils.readValue(result, TodoListQueryResDto.class);
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
