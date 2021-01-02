package com.fuar.sale.repository.mongo;

import com.fuar.sale.domain.Sale;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SaleRepository extends ReactiveMongoRepository<Sale, Long> {

}
