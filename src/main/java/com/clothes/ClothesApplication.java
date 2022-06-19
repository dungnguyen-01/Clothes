package com.clothes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClothesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClothesApplication.class, args);
    }

}
