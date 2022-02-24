package com.tianya.springcloud.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianya.springcloud.books.entity.BookVo;
import com.tianya.springcloud.books.entity.GitConfigBookBean;

@RestController
@RequestMapping("/book")
public class BooksController {
	
	@Autowired
	private GitConfigBookBean gitConfigBook;
	
	@GetMapping({"/",""})
	public BookVo getBook() {
		System.out.println("book");
		BookVo book = new BookVo();
		book.setId(1002);
		book.setName("疯狂Java");
		book.setAuthor("李刚");
		return book ;
	}
	
	
	@GetMapping("/config")
	public GitConfigBookBean getGitConfigBook() {
		return gitConfigBook ;
	}
	
	

}
