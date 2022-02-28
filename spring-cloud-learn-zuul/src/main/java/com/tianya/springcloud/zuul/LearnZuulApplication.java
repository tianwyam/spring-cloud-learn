package com.tianya.springcloud.zuul;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

// 开启网关路由代理
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class LearnZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnZuulApplication.class, args);
	}

}

