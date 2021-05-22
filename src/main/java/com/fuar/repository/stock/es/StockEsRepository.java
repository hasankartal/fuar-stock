package com.fuar.repository.stock.es;

import com.fuar.domain.stock.es.StockEs;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface StockEsRepository extends ReactiveElasticsearchRepository<StockEs, Long> {

}
