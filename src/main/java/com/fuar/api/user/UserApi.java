package com.fuar.api.user;

import com.fuar.api.sale.SaleApi;
import com.fuar.domain.user.User;
import com.fuar.model.user.UserRequestDto;
import com.fuar.model.user.UserResponseDto;
import com.fuar.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserApi {
    Logger logger = LoggerFactory.getLogger(SaleApi.class);
    private final UserService userService;

    @GetMapping
    public Flux<UserResponseDto> getAllSales() {
        return userService.getAll();
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono saveSale(@RequestBody UserRequestDto item) {
        User user = User.builder()
                .id(new Random().nextLong())
                .userName(item.getUserName())
                .password(item.getPassword())
                .build();
        userService.save(user)
                .subscribe(result -> logger.info("Entity has been saved: {}", result));

        return null;
    }
}
