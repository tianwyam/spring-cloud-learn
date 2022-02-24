package com.tianya.springcloud.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
// 开启断路器
@EnableCircuitBreaker
// 开启feign客户端
@EnableFeignClients
@SpringBootApplication
public class LearnHystrixApplication {
	public static void main(String[] args) {
		SpringApplication.run(LearnHystrixApplication.class, args);
	}
	
}
