package com.tianya.springcloud.hystrix.feign;

import org.springframework.stereotype.Service;

import com.tianya.springcloud.hystrix.entity.BookVo;

/**
 * @description
 *	回调方法
 * @author TianwYam
 * @date 2022年2月24日下午7:18:53
 */
@Service
public class BookServiceFallBack implements BookServiceFeignClient{


	@Override
	public BookVo getBookVo() {
		return BookVo.builder()
				.name("《hystrix在feign中使用》-DEFAULT")
				.author("tianwyam")
				.build();
	}

	
}
