package com.fuar.sale.service;

import com.fuar.sale.domain.Sale;
import com.fuar.sale.domain.es.SaleEs;
import com.fuar.sale.repository.es.SaleEsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SaleEsService {
    private final SaleEsRepository saleEsRepository;

    public Mono<SaleEs> saveNewSale(Sale sale) {
        Mono<SaleEs> ps = saleEsRepository.save(
                SaleEs.builder()
                        .id(sale.getId())
                        .amount(sale.getAmount())
                        .build());
        return ps;
    }

    public Flux<SaleEs> findAll() {
        return saleEsRepository.findAll();
    }
}