package com.mydddyh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.mydddyh.mapper")
@EnableSwagger2
public class WebApplicaion {
    public static void main(String[] args) {
        SpringApplication.run(WebApplicaion.class, args);
    }
}
