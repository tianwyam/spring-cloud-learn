package com.tianya.springcloud.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianya.springcloud.user.entity.BookVo;
import com.tianya.springcloud.user.feign.BooksServiceFeignClient;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private BooksServiceFeignClient booksServiceFeignClient ;
	
	@GetMapping("/book")
	public BookVo getBook() {
		System.out.println("feign");
		return booksServiceFeignClient.getBook() ;
	}
}
