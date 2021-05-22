package com.fuar.repository.sale;

import com.fuar.domain.sale.Sale;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SaleRepository extends ReactiveMongoRepository<Sale, Long> {

}
