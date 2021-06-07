package com.fuar.service.user;

import com.fuar.domain.user.User;
import com.fuar.model.user.UserResponseDto;
import com.fuar.repository.user.UserRepository;
import com.fuar.service.log.TokenService;
import com.fuar.util.TokenGenerator;
import com.fuar.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final TokenService tokenService;

    public Flux<UserResponseDto> getAll() {
        return repository.findAll().map(this::mapToDto);
    }

    private UserResponseDto mapToDto(User item) {
        if (item == null) {
            return UserResponseDto.builder()
                    .isActive(false)
                    .build();
        }
        String hashCode = null;
        try {
            hashCode = Utils.hashCode(item.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Mono<User> user = repository.findByUserNameAndPassword(item.getUserName(), hashCode);
        if(user.block() != null) {
            String token = TokenGenerator.generateNewToken();
            return UserResponseDto.builder()
                    .isActive(true)
                    .token(token)
                    .build();
        }
        return UserResponseDto.builder()
                .isActive(false)
                .build();
    }

    public Mono save(User item) {
        if (item == null) {
            return null;
        }

        String hashCode = null;
        try {
            hashCode = Utils.hashCode(item.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Mono<User> user = repository.findByUserNameAndPassword(item.getUserName(), hashCode);
        if(user.block() != null) {
            item.setPassword(hashCode);
        }
        Mono<User> userMono = repository.save(item);

        return userMono;
    }
}
