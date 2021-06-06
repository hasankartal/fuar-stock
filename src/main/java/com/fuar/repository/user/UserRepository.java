package com.fuar.repository.user;

import com.fuar.domain.user.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, Long> {

    Mono<User> findByUserNameAndPassword(String userName, String password);
}
