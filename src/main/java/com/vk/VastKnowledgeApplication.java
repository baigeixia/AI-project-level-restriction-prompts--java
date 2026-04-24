package com.vastknowledge;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.vastknowledge.mapper")
public class VastKnowledgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(VastKnowledgeApplication.class, args);
    }
}
