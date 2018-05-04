package com.feng.webflux.router;

import com.feng.webflux.handler.TimeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class RouterConfig {

    @Autowired
    private TimeHandler timeHandler;

    @Bean
    public RouterFunction<ServerResponse> timeRouter(){
        return RouterFunctions.route(GET("/getTime"),timeHandler::getTime)
                .andRoute(GET("/getDate"),req -> timeHandler.getDate(req))
                .andRoute(GET("/times"),timeHandler::sendTimePerSec);
    }
}
