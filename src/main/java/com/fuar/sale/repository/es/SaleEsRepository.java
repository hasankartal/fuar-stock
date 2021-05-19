package com.fuar.sale.repository.es;

import com.fuar.sale.domain.Sale;
import com.fuar.sale.domain.es.SaleEs;
import org.elasticsearch.common.collect.List;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SaleEsRepository extends ReactiveElasticsearchRepository<SaleEs, Long> {

    Mono<Void> deleteById(Long id);
    Flux<SaleEs> findByAmount(Float amount);
}
