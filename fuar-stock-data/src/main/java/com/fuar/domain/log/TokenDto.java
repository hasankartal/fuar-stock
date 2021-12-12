package com.fuar.domain.log;

import lombok.*;

@Data
@Builder
public class TokenDto {

    private Long id;
    private Boolean isActive;
    private String token;

}