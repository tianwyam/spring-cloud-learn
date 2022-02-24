package com.tianya.springcloud.hystrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tianya.springcloud.hystrix.entity.BookVo;
import com.tianya.springcloud.hystrix.feign.BookServiceFeignClient;

@RestController
@RequestMapping("/book")
public class BooksController {
	
	@Autowired
	private BookServiceFeignClient bookServiceFeignClient;
	
	
	@Autowired
	private RestTemplate restTemplate ;
	
	@GetMapping({"","/"})
	// 熔断器配置（方式一）
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
	
	
	
	/**
	 * @description
	 *	采用feign方式调用
	 * @author TianwYam
	 * @date 2022年2月24日下午7:25:03
	 * @return
	 */
	@GetMapping("/feign")
	public BookVo getFeignBookVo() {
		return bookServiceFeignClient.getBookVo();
	}
}
