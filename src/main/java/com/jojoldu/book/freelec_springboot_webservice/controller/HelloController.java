package com.jojoldu.book.freelec_springboot_webservice.controller;

import com.jojoldu.book.freelec_springboot_webservice.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam String name, @RequestParam Integer amount) {
        return new HelloResponseDto(name, amount);
    }
}
