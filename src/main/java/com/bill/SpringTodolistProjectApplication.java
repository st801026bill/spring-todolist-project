package com.bill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringTodolistProjectApplication {

	public static void main(String[] args) {
			SpringApplication.run(SpringTodolistProjectApplication.class, args);
	}
}
