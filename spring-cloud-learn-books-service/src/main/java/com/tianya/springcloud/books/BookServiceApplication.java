package com.tianya.springcloud.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.tianya.springcloud.books.entity.GitConfigBookBean;

@EnableEurekaClient
@SpringBootApplication
@EnableConfigurationProperties(value = GitConfigBookBean.class)
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}
	
}
