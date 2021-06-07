package com.fuar.model.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponseDto {

    private String token;
    private Boolean isActive;
}
