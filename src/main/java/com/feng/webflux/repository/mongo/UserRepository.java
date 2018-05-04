package com.feng.webflux.repository.mongo;

import com.feng.webflux.entity.SysUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<SysUser, String> {

    Mono<SysUser> findByUsername(String username);

    Mono<Long> deleteByUsername(String username);
}