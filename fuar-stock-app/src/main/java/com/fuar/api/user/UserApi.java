package com.fuar.api.user;

import com.fuar.api.sale.SaleApi;
import com.fuar.entity.user.User;
import com.fuar.domain.user.UserRequestDto;
import com.fuar.domain.user.UserResponseDto;
import com.fuar.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserApi {
    Logger logger = LoggerFactory.getLogger(SaleApi.class);
    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getAllSales() {
        return userService.getAll();
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveSale(@RequestBody UserRequestDto item) {
        User user = new User();
        user.setUserName(item.getUserName());
        user.setPassword(item.getPassword());

        userService.save(user);

        return null;
    }
}
