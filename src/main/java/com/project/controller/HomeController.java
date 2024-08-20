package com.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public  String home(){
        return "Hii";
    }

    @GetMapping("/api")
    public  String secure(){
        return "Bye";
    }

}
