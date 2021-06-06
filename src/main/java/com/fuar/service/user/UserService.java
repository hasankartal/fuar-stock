package com.fuar.service.user;

import com.fuar.domain.sale.Sale;
import com.fuar.domain.stock.Stock;
import com.fuar.domain.stock.es.StockEs;
import com.fuar.domain.user.User;
import com.fuar.model.sale.SaleSaveRequest;
import com.fuar.model.stock.StockResponse;
import com.fuar.model.stock.StockSaveRequest;
import com.fuar.model.user.UserRequest;
import com.fuar.model.user.UserResponse;
import com.fuar.repository.stock.StockRepository;
import com.fuar.repository.user.UserRepository;
import com.fuar.service.log.TokenService;
import com.fuar.service.stock.es.StockEsService;
import com.fuar.util.TokenGenerator;
import com.fuar.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final TokenService tokenService;

    public Flux<UserResponse> getAll() {
        return repository.findAll().map(this::mapToDto);
    }

    private UserResponse mapToDto(User item) {
        if (item == null) {
            return UserResponse.builder()
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
            return UserResponse.builder()
                    .isActive(true)
                    .token(token)
                    .build();
        }
        return UserResponse.builder()
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
