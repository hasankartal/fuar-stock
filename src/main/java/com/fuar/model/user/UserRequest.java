package com.fuar.model.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRequest {

    private String userName;
    private String password;
}
