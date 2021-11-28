package com.fuar.repository.sale;

import com.fuar.domain.sale.Sale;
import com.fuar.domain.sale.es.SaleEs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface SaleRepository extends MongoRepository<Sale, Long> {

    List<Sale> findByMoney(String money);
}
