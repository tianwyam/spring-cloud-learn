package com.tianya.springcloud.books.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianya.springcloud.books.entity.BookVo;

@RestController
public class BooksController {
	
	
	@GetMapping("/book")
	public BookVo getBook() {
		System.out.println("book");
		BookVo book = new BookVo();
		book.setId(1002);
		book.setName("疯狂Java");
		book.setAuthor("李刚");
		return book ;
	}

}
