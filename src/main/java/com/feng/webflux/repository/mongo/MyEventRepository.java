package com.feng.webflux.repository.mongo;

import com.feng.webflux.entity.MyEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface MyEventRepository extends ReactiveMongoRepository<MyEvent, Long> { // 1

    @Tailable
    /**
     * @Tailable注解的作用类似于linux的tail命令，被注解的方法将发送无限流，
     * 需要注解在返回值为Flux这样的多个元素的Publisher的方法上；
     * findAll()是想要的方法，但是在ReactiveMongoRepository中我们够不着，所以使用findBy()代替。
     */
    Flux<MyEvent> findBy(); // 2
}