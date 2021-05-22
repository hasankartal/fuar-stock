package com.fuar.service.log;

import com.fuar.domain.log.LogRestIncoming;
import com.fuar.domain.log.Token;
import com.fuar.model.log.LogRestIncomingRequest;
import com.fuar.model.log.TokenRequest;
import com.fuar.repository.log.LogRestIncomingRepository;
import com.fuar.repository.log.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository repository;

    public boolean isActive(String token) {
        return true;
    }

    public Mono<Token> save(TokenRequest request) {
        return repository.save(Token
                .builder()
                .id(request.getId())
                .isActive(true)
                .token(request.getToken())
                .build());
    }
}
