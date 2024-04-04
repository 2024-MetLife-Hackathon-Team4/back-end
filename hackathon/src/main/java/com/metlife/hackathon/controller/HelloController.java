package com.metlife.hackathon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("")
    public String index() {
        return "hello22222";
    }

    @PostMapping("")
    public String post() {
        return "hello post";
    }
}
