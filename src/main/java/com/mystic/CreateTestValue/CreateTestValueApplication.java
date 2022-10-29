package com.mystic.CreateTestValue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author mystic
 * EnableAsync注解,开放异步调用
 */
@EnableAsync
@SpringBootApplication
public class CreateTestValueApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreateTestValueApplication.class, args);
    }

}
