package com.fuar.model.log;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
public class TokenRequest {

    private Long id;
    private Boolean isActive;
    private String token;

}