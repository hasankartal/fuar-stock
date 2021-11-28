package com.fuar.domain.log;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "token")
@Getter
@Setter
@Builder
public class Token {

    @Id
    private Long id;
    private Boolean isActive;
    private String token;
}