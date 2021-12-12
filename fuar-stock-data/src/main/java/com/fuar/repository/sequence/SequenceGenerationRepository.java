package com.fuar.repository.sequence;

import com.fuar.entity.DatabaseSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceGenerationRepository extends JpaRepository<DatabaseSequence, String> {
//    Mono<DatabaseSequence> findBySeq(Long seq);
}

