package com.tianya.springcloud.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
// 开启断路器
@EnableCircuitBreaker
@SpringBootApplication
public class LearnHystrixApplication {
	public static void main(String[] args) {
		SpringApplication.run(LearnHystrixApplication.class, args);
	}
	
}
