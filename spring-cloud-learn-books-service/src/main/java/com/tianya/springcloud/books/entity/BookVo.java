package com.tianya.springcloud.books.entity;

public class BookVo {
	
	private long id ;
	private String name ;
	private String author ;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Override
	public String toString() {
		return "BookVo [id=" + id + ", name=" + name + ", author=" + author + "]";
	}

}
