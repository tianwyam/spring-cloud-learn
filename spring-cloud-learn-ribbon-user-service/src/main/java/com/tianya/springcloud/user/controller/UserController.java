package com.tianya.springcloud.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tianya.springcloud.user.entity.BookVo;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Value("${ribbon.client.name}")
	private String bookServiceAppName ;

	@Autowired
	private RestTemplate restTemplate ;
	
	@GetMapping("/book")
	public BookVo getBook() {
		System.out.println(bookServiceAppName);
		String url = String.format("http://%s/%s", bookServiceAppName, "book" ) ;
		System.out.println(url);
		return restTemplate.getForObject(url, BookVo.class);
	}
}
