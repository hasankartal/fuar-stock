package com.fuar.model.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {

    private String token;
    private Boolean isActive;
}
