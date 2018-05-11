package com.feng.webflux.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

@Configuration
@Slf4j
public class MongoConfig {

    @Bean   // 1
    public CommandLineRunner initData(MongoOperations mongo) {  // 2
        return (String... args) -> {    // 3
            log.info("init event collection!");
//            mongo.dropCollection(MyEvent.class);    // 4
//            mongo.createCollection(MyEvent.class, CollectionOptions.empty().maxDocuments(200).size(200000).capped()); // 5
        };
    }
}
