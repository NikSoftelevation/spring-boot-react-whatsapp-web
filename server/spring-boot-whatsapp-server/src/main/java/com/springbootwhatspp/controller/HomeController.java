package com.springbootwhatspp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<String> homeResponseEntity() {
        return new ResponseEntity<>("Welcome to our Whatsapp API using Spring Boot", HttpStatus.OK);
    }
}