package com.fuar.service.stock.es;

import com.fuar.domain.stock.Stock;
import com.fuar.domain.stock.es.StockEs;
import com.fuar.repository.stock.es.StockEsRepository;
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
