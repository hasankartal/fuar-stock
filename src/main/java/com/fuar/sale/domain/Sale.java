package com.fuar.sale.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sale")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class Sale {

    @Id
    private Long id;
    private Float amount;
}
