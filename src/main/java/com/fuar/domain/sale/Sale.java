package com.fuar.domain.sale;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "sale")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class Sale {

    @Id
    private Long id;
    private Float amount;
    private String money;
    private Date orderDate;
}
