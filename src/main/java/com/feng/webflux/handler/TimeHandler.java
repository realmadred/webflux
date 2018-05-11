package com.feng.webflux.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class TimeHandler {

    public Mono<ServerResponse> getTime(ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(String.format("现在的时间是：%s",LocalDateTime.now())),String.class);
    }

    public Mono<ServerResponse> getDate(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(String.format("现在的日期是：%s",LocalDate.now())),String.class);
    }

    public Mono<ServerResponse> sendTimePerSec(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(Flux.interval(Duration.ofMillis(200))
                        .map(i -> String.format("time is: %s",LocalDateTime.now())),String.class);
    }

}
