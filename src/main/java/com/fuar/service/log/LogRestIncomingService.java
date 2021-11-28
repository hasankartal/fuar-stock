package com.fuar.service.log;

import com.fuar.domain.log.LogRestIncoming;
import com.fuar.model.log.LogRestIncomingDto;
import com.fuar.repository.log.LogRestIncomingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LogRestIncomingService {
    private final LogRestIncomingRepository repository;

    public LogRestIncoming save(LogRestIncomingDto request) {
        return  repository.save(LogRestIncoming.
                builder()
                .id(request.getId())
                .clientIp(request.getClientIp())
                .contentPayload(request.getContentPayload())
                .elapsedTime(request.getElapsedTime())
                .endpointUrl(request.getEndpointUrl())
                .endTime(request.getEndTime())
                .operationType(request.getOperationType())
                .serverIp(request.getServerIp())
                .startTime(request.getStartTime())
                .build());

    }
}
