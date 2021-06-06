package com.fuar.repository.sequence;

import com.fuar.domain.DatabaseSequence;
import com.fuar.domain.user.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SequenceGenerationRepository extends ReactiveMongoRepository<DatabaseSequence, String> {
//    Mono<DatabaseSequence> findBySeq(Long seq);

}

