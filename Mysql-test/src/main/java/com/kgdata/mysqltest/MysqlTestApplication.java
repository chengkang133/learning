package com.kgdata.mysqltest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.kgdata")
@MapperScan("com.kgdata.mysqltest.dao")
public class MysqlTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysqlTestApplication.class, args);
	}

}
