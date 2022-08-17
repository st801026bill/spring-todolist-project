package com.bill.service.observe;

import com.bill.dto.TodoListQueryResDto;
import com.bill.entity.TodoList;
import com.bill.util.JedisUtils;
import com.bill.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisService implements IObserveService, ICacheService {

    @Autowired
    private JedisUtils jedisUtils;
    @Autowired
    private JsonUtils jsonUtils;

    private static final String KEY = "todoList";

    @Override
    public TodoListQueryResDto queryTodo(Integer seqNo) {
        //method 1
        Map<String, String> map = jedisUtils.hgetall(KEY);
        return jsonUtils.readValue(MapUtils.getString(map, String.valueOf(seqNo)), new TypeReference<TodoListQueryResDto>(){});
        //method 2
//        Optional<TodoListQueryResDto> opt = queryTodoList().stream().filter(todo -> seqNo == todo.getSeqNo()).findFirst();
//        return opt.orElse(null);
    }

    @Override
    public List<TodoListQueryResDto> queryTodoList() {
        Map<String, String> map = jedisUtils.hgetall(KEY);
        return map.values().stream().map(v ->
            jsonUtils.readValue(v, new TypeReference<TodoListQueryResDto>(){})).collect(Collectors.toList());
    }

    @Override
    public void updateCache(List<TodoList> todoList) {
        HashMap<String, String> map = todoList.parallelStream().collect(
            Collectors.toMap(e -> String.valueOf(e.getSeqNo()), e -> jsonUtils.writeValueAsString(e), (prev, next) -> next, HashMap::new));
        if(map.size()>0) jedisUtils.hmset(KEY, map);
    }
}
