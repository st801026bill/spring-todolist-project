package com.bill.dao;

import com.bill.entity.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoListRepository extends JpaRepository<TodoList, Integer> {
		public List<TodoList> findBySeqNo(int seqNo);
}
