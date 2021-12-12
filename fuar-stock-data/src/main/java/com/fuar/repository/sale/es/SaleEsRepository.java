package com.fuar.repository.sale.es;

import com.fuar.entity.sale.es.SaleEs;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SaleEsRepository extends ReactiveElasticsearchRepository<SaleEs, Long> {

    Mono<Void> deleteById(Long id);
    Flux<SaleEs> findByAmount(Float amount);

    Flux<SaleEs> findByMoney(String money);
}
