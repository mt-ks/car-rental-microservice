package com.mtks.carrent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {


    @GetMapping("/test")
    public String test(@Value("${spring.application.name}") String name) {
        return "Test App Name: " + name;
    }

}
