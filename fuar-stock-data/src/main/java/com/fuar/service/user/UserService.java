package com.fuar.service.user;

import com.fuar.entity.user.User;
import com.fuar.domain.user.UserResponseDto;
import com.fuar.repository.user.UserRepository;
import com.fuar.service.log.TokenService;
import com.fuar.util.TokenGenerator;
import com.fuar.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    public List<UserResponseDto> getAll() {
        List<User> userList = repository.findAll();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for (User user : userList) {
            UserResponseDto userResponseDto = mapToDto(user);
            userResponseDtoList.add(userResponseDto);
        }
        return userResponseDtoList;
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
        User user = repository.findByUserNameAndPassword(item.getUserName(), hashCode);
        if(user != null) {
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

    public User save(User item) {
        if (item == null) {
            return null;
        }

        String hashCode = null;
        try {
            hashCode = Utils.hashCode(item.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = repository.findByUserNameAndPassword(item.getUserName(), hashCode);
        if(user != null) {
            user.setPassword(hashCode);
        }
        user = repository.save(user);

        return user;
    }
}
