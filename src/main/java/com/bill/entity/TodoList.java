package com.bill.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "todo_list")
public class TodoList implements Serializable {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "seq_no", nullable = false)
		private Integer seqNo;

		@Column(name = "todo", nullable = false)
		private String todo;

		@Column(name = "is_done", nullable = false)
		private Boolean isDone;

		@Column(name = "create_date_time", nullable = false)
		private LocalDateTime createDateTime;

		@Column(name = "update_date_time", nullable = false)
		private LocalDateTime updateDateTime;

		public TodoList(Integer seqNo, String todo, Boolean isDone) {
				this(todo, isDone);
				this.seqNo = seqNo;
		}

		public TodoList(String todo, Boolean isDone) {
				this.todo = todo;
				this.isDone = false;
				this.createDateTime = LocalDateTime.now();
				this.updateDateTime = LocalDateTime.now();
		}

		public TodoList(Integer seqNo) {
				this.seqNo = seqNo;
		}
}
