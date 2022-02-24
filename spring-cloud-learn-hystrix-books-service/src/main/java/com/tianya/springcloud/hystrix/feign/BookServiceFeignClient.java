package com.tianya.springcloud.hystrix.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.tianya.springcloud.hystrix.entity.BookVo;

// 开启feign客户端 fallback 配置回调
@FeignClient(value = "BOOKS-SERVICE", fallback = BookServiceFallBack.class)
public interface BookServiceFeignClient {
	
	@GetMapping("/book")
	public BookVo getBookVo();
	
	
}
