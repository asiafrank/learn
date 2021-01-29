package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<?> f = executorService.submit(() -> {
		});
//		f.get()
	}

}
