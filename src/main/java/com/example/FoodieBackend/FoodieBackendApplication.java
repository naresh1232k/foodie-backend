package com.example.FoodieBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
public class FoodieBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodieBackendApplication.class, args);
	}
}
