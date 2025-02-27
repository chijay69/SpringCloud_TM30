package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderMain {
    public static void main(String[] args) {
        System.out.println("Hello, order!");
        SpringApplication.run(OrderMain.class);
    }
}