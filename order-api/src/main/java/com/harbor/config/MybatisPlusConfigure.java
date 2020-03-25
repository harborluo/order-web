package com.harbor.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.harbor.mapper")
public class MybatisPlusConfigure {
}
