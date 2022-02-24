package com.tianya.springcloud.hystrix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tianya.springcloud.hystrix.entity.BookVo;

@Service
public class BooksService {
	

	@Autowired
	private RestTemplate restTemplate ;

	// 用在service方法上一样的（并且方法还可以带参数）
	@HystrixCommand(fallbackMethod = "defaultBook")
	public BookVo getBook() {
		return restTemplate.getForObject("http://localhost:9090/book/", BookVo.class);
	}

	/**
	 * @description
	 *	短路器 回调方法（容错）
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
