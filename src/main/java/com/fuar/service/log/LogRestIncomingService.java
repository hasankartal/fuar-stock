package com.fuar.service.log;

import com.fuar.domain.log.LogRestIncoming;
import com.fuar.model.log.LogRestIncomingDto;
import com.fuar.repository.log.LogRestIncomingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LogRestIncomingService {
    @Autowired
    private LogRestIncomingRepository repository;

    public LogRestIncoming save(LogRestIncomingDto request) {
        LogRestIncoming logRestIncoming = new LogRestIncoming();
        logRestIncoming.setClientIp(request.getClientIp());
        logRestIncoming.setContentPayload(request.getContentPayload());
        logRestIncoming.setElapsedTime(request.getElapsedTime());
        logRestIncoming.setEndpointUrl(request.getEndpointUrl());
        logRestIncoming.setEndTime(request.getEndTime());
        logRestIncoming.setOperationType(request.getOperationType());
        logRestIncoming.setServerIp(request.getServerIp());
        logRestIncoming.setStartTime(request.getStartTime());

        return  repository.save(logRestIncoming);
    }
}
