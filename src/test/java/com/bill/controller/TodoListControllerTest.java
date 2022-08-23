package com.bill.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.bill.SpringTodolistProjectApplication;
import com.bill.dao.TodoListDao;
import com.bill.dto.TodoListCreateReqDto;
import com.bill.dto.TodoListDeleteReqDto;
import com.bill.dto.TodoListQueryResDto;
import com.bill.dto.TodoListUpdateReqDto;
import com.bill.entity.TodoList;
import com.bill.service.TodoListService;
import com.bill.service.observe.ConcurrentMapService;
import com.bill.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringTodolistProjectApplication.class)
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@Slf4j
public class TodoListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonUtils jsonUtils;

    @Autowired
    private TodoListService todoListService;
    @Autowired
    private ConcurrentMapService concurrentMapService;
    @MockBean
    private TodoListDao todoListDao;

    @Test
    @Order(1)
    void addTodo() throws Exception {
        TodoListQueryResDto expectTodo = initExceptTodoTest(
            "addTodoResponse.json", new TypeReference<TodoListQueryResDto>(){});
        List<TodoList> expectTodoList = mockTodoList(false);
        BDDMockito.given(todoListDao.save(ArgumentMatchers.any())).willReturn(IterableUtils.get(expectTodoList, 0));
        BDDMockito.given(todoListDao.findAll()).willReturn(expectTodoList);
        todoListService.createTodoList(new TodoListCreateReqDto("代辦事項4"));

        TodoListQueryResDto todo = concurrentMapService.queryTodo(4);
        assertEquals(expectTodo, todo);
    }

    @Test
    @Order(2)
    void updateTodo() throws Exception {
        TodoListQueryResDto expectTodo = initExceptTodoTest(
            "updateTodoResponse.json", new TypeReference<TodoListQueryResDto>(){});
        List<TodoList> expectTodoList = mockTodoList(true);
        BDDMockito.given(todoListDao.save(ArgumentMatchers.any())).willReturn(IterableUtils.get(expectTodoList, 0));
        BDDMockito.given(todoListDao.findAll()).willReturn(expectTodoList);
        todoListService.updateTodoList(new TodoListUpdateReqDto(4, "代辦事項4", true));

        TodoListQueryResDto todo = concurrentMapService.queryTodo(4);
        assertEquals(expectTodo, todo);
    }

    @Test
    @Order(3)
    void deleteTodo() throws Exception {
        todoListService.deleteTodoList(new TodoListDeleteReqDto(4));
        BDDMockito.given(todoListDao.findAll()).willReturn(List.of());
        TodoListQueryResDto todo = concurrentMapService.queryTodo(4);
        assertNull(todo);
    }

    private <T> T initExceptTodoTest(String exceptFileName, TypeReference<T> clazz)
        throws Exception {
        String responseJson = jsonUtils.getInput(exceptFileName);
        T expectTodo = jsonUtils.readValue(responseJson, clazz);

        log.info("expectTodo: {}", expectTodo);
        return expectTodo;
    }

//    private <T> T initQueryTodoTest(String url, TypeReference<T> clazz) throws Exception {
//        String result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
//        return jsonUtils.readValue(result, clazz);
//    }

    private List<TodoList> mockTodoList(Boolean isDone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime dttm = LocalDateTime.parse("2022-08-10 17:43:12.909428", formatter);
        return List.of(new TodoList(4, "代辦事項4", isDone, dttm, dttm));
    }
}
