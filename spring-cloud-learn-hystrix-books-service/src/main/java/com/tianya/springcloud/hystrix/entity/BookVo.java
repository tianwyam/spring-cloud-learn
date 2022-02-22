package com.tianya.springcloud.hystrix.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookVo {
	
	private String name ;
	
	private String author ;

}
