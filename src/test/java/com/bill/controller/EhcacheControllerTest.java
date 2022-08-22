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
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.fasterxml.jackson.core.type.TypeReference;
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
        TodoListQueryResDto expectTodo = initExceptTodoTest(
                "queryTodoResponse.json", new TypeReference<TodoListQueryResDto>(){});
        Mockito.when(service.queryTodo(1)).thenReturn(expectTodo);
        TodoListQueryResDto resDto = initQueryTodoTest("/ehcache/1", new TypeReference<TodoListQueryResDto>(){});

        Mockito.when(service.queryTodo(1)).thenReturn(expectTodo);
        log.info("resDto: {}", resDto);
        assertEquals(expectTodo, resDto);
    }

    @Test
    @Order(2)
    void queryTodoNotEqual() throws Exception {
        TodoListQueryResDto expectTodo = initExceptTodoTest(
                "queryTodoResponse.json", new TypeReference<TodoListQueryResDto>(){});
        Mockito.when(service.queryTodo(1)).thenReturn(expectTodo);
        TodoListQueryResDto resDto = initQueryTodoTest("/ehcache/1", new TypeReference<TodoListQueryResDto>(){});

        log.info("resDto: {}", resDto);
        resDto.setIsDone(true);
        assertNotEquals(expectTodo, resDto);
    }

    @Test
    @Order(3)
    void queryTodoList() throws Exception {
        List<TodoListQueryResDto> expectTodoList = initExceptTodoTest(
                "queryTodoListResponse.json", new TypeReference<List<TodoListQueryResDto>>(){});
        Mockito.when(service.queryTodoList()).thenReturn(expectTodoList);
        List<TodoListQueryResDto> resDtoList = initQueryTodoTest("/ehcache", new TypeReference<List<TodoListQueryResDto>>(){});

        assertEquals(expectTodoList, resDtoList);
    }

    private <T>T initExceptTodoTest(String exceptFileName, TypeReference<T> clazz) throws Exception {
        String responseJson = jsonUtils.getInput(exceptFileName);
        T expectTodo = jsonUtils.readValue(responseJson, clazz);

        log.info("expectTodo: {}", expectTodo);
        return expectTodo;
    }

    private <T>T initQueryTodoTest(String url, TypeReference<T> clazz) throws Exception {
        String result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return jsonUtils.readValue(result, clazz);
    }
}
