package com.xyz.keshe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = {"com.xyz.keshe.mapper"})
public class UpicShowApplication {

//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(UpicShowApplication.class);
//    }
	public static void main(String[] args) {
		SpringApplication.run(UpicShowApplication.class, args);
	}
}
