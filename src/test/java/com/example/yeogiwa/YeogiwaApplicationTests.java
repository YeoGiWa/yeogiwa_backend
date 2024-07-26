package com.example.yeogiwa;

import com.example.yeogiwa.controller.UserControllerTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YeogiwaApplicationTests {
	@Autowired
	private UserControllerTest userControllerTest;

	@Test
	void contextLoads() throws Exception{
		assertThat(userControllerTest).isNotNull();
	}

}
