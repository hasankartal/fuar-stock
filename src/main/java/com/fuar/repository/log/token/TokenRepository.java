package com.fuar.repository.log.token;

import com.fuar.domain.log.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TokenRepository extends MongoRepository<Token, Long> {

}
