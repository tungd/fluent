package com.eventmap.fluent;

import com.eventmap.fluent.controller.FluentMainController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/")
public class Main {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FluentMainController.class, args);
    }
}
