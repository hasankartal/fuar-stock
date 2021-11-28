package com.fuar.repository.stock;

import com.fuar.domain.stock.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StockRepository extends MongoRepository<Stock, Long> {

}
