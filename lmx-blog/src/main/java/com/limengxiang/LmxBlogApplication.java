package com.limengxiang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Random;
import java.util.stream.IntStream;


@SpringBootApplication
@EnableScheduling
@MapperScan("com.limengxiang.mapper")
public class LmxBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LmxBlogApplication.class, args);
//		ApplicationContext context = SpringApplication.run(LmxBlogApplication.class, args);
//		Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
    }
}
