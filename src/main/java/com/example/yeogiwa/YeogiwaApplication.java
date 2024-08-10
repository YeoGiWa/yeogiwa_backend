package com.example.yeogiwa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class YeogiwaApplication {

	public static void main(String[] args) {
		SpringApplication.run(YeogiwaApplication.class, args);
	}

}
