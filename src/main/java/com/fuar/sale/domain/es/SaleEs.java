package com.fuar.sale.domain.es;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "sale")
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class SaleEs {

    @Id
    private Long id;
    private Float amount;
}
