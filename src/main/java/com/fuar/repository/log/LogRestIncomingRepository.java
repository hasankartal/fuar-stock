package com.fuar.repository.log;

import com.fuar.domain.log.LogRestIncoming;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

public interface LogRestIncomingRepository extends ReactiveMongoRepository<LogRestIncoming, Long> {

}
