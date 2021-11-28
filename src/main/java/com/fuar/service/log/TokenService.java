package com.fuar.service.log;

import com.fuar.domain.log.Token;
import com.fuar.model.log.TokenDto;
import com.fuar.repository.log.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    @Autowired
    private TokenRepository repository;

    public boolean isActive(String token) {
        return true;
    }

    public Token save(TokenDto request) {
        Token token = new Token();
        token.setIsActive(true);
        token.setToken(request.getToken());

        return repository.save(token);
    }
}
