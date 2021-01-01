package com.fuar.stock.repository.es;

import com.fuar.stock.domain.es.StockEs;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StockEsRepository extends ReactiveElasticsearchRepository<StockEs, Long> {

}
