package com.fuar.domain.stock;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stock")
@Getter
@Setter
@Builder
public class Stock {

    @Id
    private Long id;
}
