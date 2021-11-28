package com.fuar.service.log;

import com.fuar.domain.log.Token;
import com.fuar.model.log.TokenDto;
import com.fuar.repository.log.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository repository;

    public boolean isActive(String token) {
        return true;
    }

    public Token save(TokenDto request) {
        return repository.save(Token
                .builder()
                .id(request.getId())
                .isActive(true)
                .token(request.getToken())
                .build());
    }
}
