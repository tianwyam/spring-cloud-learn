package com.tianya.springcloud.books.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "book")
public class GitConfigBookBean {
	
	private String id ;
	
	private String name ;
	
	private String author ;
	

}
