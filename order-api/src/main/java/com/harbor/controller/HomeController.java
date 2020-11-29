package com.harbor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String homePage(){
        log.info("go to home page: index.html");
        return "index";
    }


    @GetMapping("/view/{name}")
    public String view(@PathVariable("name") String name){
        return name;
    }
}
