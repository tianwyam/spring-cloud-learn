package com.tianya.springcloud.hystrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tianya.springcloud.hystrix.entity.BookVo;

@RestController
@RequestMapping("/book")
public class BooksController {
	
	@Autowired
	private RestTemplate restTemplate ;
	
	@GetMapping({"","/"})
	@HystrixCommand(fallbackMethod = "defaultBook")
	public BookVo getBook() {
		return restTemplate.getForObject("http://localhost:9090/book/", BookVo.class);
	}

	/**
	 * @description
	 *	短路器 默認執行方法
	 * @author TianwYam
	 * @date 2022年2月22日下午9:01:06
	 * @return
	 */
	public BookVo defaultBook() {
		return BookVo.builder()
				.name("《肖生克的救贖-DEFAULT》")
				.author("tianwyam").build();
	}
	
	
}
