package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductMain {
    public static void main(String[] args) {
        System.out.println("Hello, product!");
        SpringApplication.run(ProductMain.class);
    }
}