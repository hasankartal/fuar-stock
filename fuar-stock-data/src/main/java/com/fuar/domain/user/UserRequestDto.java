package com.fuar.domain.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRequestDto {

    private String userName;
    private String password;
}
