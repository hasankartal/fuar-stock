package com.fuar.service.sequence;

import com.fuar.domain.DatabaseSequence;
import com.fuar.repository.sequence.SequenceGenerationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SequenceGeneratorService {

    private final SequenceGenerationRepository repository;

    public long generateSequence(String seqName) {
        DatabaseSequence mono = repository.findById(seqName).orElse(null);
        if(mono != null) {
            DatabaseSequence db = new DatabaseSequence();
            db.setId(seqName);
            long seq = mono.getSeq() + 1;
            db.setSeq(seq);

            repository.save(db);
            return seq;
        } else {
            DatabaseSequence db = new DatabaseSequence();
            db.setId(seqName);
            long seq = 1;
            db.setSeq(seq);

            repository.save(db);
            return seq;
        }
/*
        DatabaseSequence counter = repository.findAndModify(query(where("id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
*/
    }
}

