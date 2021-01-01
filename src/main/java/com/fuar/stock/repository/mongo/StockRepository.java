package com.fuar.stock.repository.mongo;

import com.fuar.stock.domain.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StockRepository extends ReactiveMongoRepository<Stock, Long> {

}
