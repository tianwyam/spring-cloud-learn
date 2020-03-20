package com.tianya.springcloud.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.tianya.springcloud.user.entity.BookVo;


@FeignClient("books-service")
public interface BooksServiceFeignClient {

	
	// // 两个坑：1. @GetMapping不支持   2. @PathVariable得设置value
	// get 请求不会成功，只要参数是复杂对象，即使指定了是GET方法，feign依然会以POST方法进行发送请求。可能是我没找到相应的注解或使用方法错误。
	
	@GetMapping("/book")
	public BookVo getBook() ;
	
}
