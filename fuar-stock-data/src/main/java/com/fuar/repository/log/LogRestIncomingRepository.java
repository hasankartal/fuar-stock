package com.fuar.repository.log;

import com.fuar.entity.log.LogRestIncoming;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRestIncomingRepository extends JpaRepository<LogRestIncoming, Long> {

}
