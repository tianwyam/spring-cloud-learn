package com.tianya.springcloud.books.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/",""})
public class IndexController {

    @Value("${spring.application.name:books-server}")
    private String appName ;

    @GetMapping
    public String index(){
        return appName ;
    }

}
