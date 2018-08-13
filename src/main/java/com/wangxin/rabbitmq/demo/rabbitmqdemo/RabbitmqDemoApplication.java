package com.wangxin.rabbitmq.demo.rabbitmqdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class RabbitmqDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqDemoApplication.class, args);
	}
}
