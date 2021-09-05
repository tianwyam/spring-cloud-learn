package com.tianya.springcloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// 开启eureka服务注册中心配置
@EnableEurekaServer
@SpringBootApplication
public class LearnEurekaServerApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(LearnEurekaServerApplication.class, args);
	}
}
