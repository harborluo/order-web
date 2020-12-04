package com.harbor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;


@SpringBootApplication(scanBasePackages={"com.harbor.config"
		,"com.harbor.controller",
		"com.harbor.service",
		"com.harbor.mapper",
        "com.harbor.exception"})
@MapperScan("com.harbor.mapper")
public class OrderApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApiApplication.class, args);
	}

}
