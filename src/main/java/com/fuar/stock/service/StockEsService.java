package com.fuar.stock.service;

import com.fuar.stock.domain.Stock;
import com.fuar.stock.domain.es.StockEs;
import com.fuar.stock.repository.es.StockEsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StockEsService {

    private final StockEsRepository stockEsRepository;

    public Mono<StockEs> saveNewStock(Stock stock) {
        Mono<StockEs> ps = stockEsRepository.save(
                StockEs.builder()
                        .id(stock.getId())
                        .build());
        return ps;
    }
    public Flux<StockEs> findAll() {
        return stockEsRepository.findAll();
    }
}
