package com.fuar.domain.customer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
@Getter
@Setter
@Builder
public class Customer {

    @Id
    private Long id;

    private String name;
    private String surname;

}
