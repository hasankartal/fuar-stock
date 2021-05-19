package com.fuar.sale.service;

import com.fuar.sale.domain.Sale;
import com.fuar.sale.domain.es.SaleEs;
import com.fuar.sale.repository.es.SaleEsRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SaleEsService {
    Logger logger = LoggerFactory.getLogger(SaleEsService.class);

    private final SaleEsRepository saleEsRepository;

    public Mono<SaleEs> saveNewSale(Sale sale) {
        Mono<SaleEs> ps = saleEsRepository.save(
                SaleEs.builder()
                        .id(sale.getId())
                        .amount(sale.getAmount())
                        .money(sale.getMoney())
                        .orderDate(new Date())
                        .build());
        return ps;
    }

    public Flux<SaleEs> findAll() {
        return saleEsRepository.findAll();
    }

    public void delete(Long id) {
        Mono<SaleEs> saleEs = saleEsRepository.findById(id);
        saleEsRepository.deleteById(id);
    }
}