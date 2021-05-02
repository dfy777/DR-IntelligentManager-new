package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.example.demo.mapper")
@SpringBootApplication
public class IntelligentManagerApplication {

	public static void main(String[] args) {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow","|{}?&");
		SpringApplication.run(IntelligentManagerApplication.class, args);
	}

}
