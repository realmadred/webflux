package com.feng.webflux.controller;

import com.feng.webflux.entity.SysUser;
import com.feng.webflux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public Mono<SysUser> save(SysUser user) {
        return this.userService.save(user);
    }

    @DeleteMapping("/{username}")
    public Mono<Long> deleteByUsername(@PathVariable String username) {
        return this.userService.deleteByUsername(username);
    }

    @GetMapping("/{username}")
    public Mono<SysUser> findByUsername(@PathVariable String username) {
        return this.userService.findByUsername(username);
    }

    @GetMapping(value = "",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    // 以流的形式返回 需要application/stream+json
    public Flux<SysUser> findAll() {
        return this.userService.findAll().delayElements(Duration.ofMillis(200));
    }
}