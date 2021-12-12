package com.fuar.entity.stock.es;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "stock")
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class StockEs {

    @Id
    private Long id;
}
