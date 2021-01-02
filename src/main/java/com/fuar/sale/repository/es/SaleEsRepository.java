package com.fuar.sale.repository.es;

import com.fuar.sale.domain.es.SaleEs;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface SaleEsRepository extends ReactiveElasticsearchRepository<SaleEs, Long> {
}
