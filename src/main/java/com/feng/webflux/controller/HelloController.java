package com.feng.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hello")
@Slf4j
/**
 * 基于spring mvc 的方式实现的webFlux
 */
public class HelloController {

    @GetMapping("/say")
    public Mono<String> hello(){
        return Mono.just("hello web flux!");
    }

}
