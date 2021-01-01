package com.fuar.stock.service;

import com.fuar.stock.domain.Stock;
import com.fuar.stock.domain.es.StockEs;
import com.fuar.stock.model.StockResponse;
import com.fuar.stock.model.StockSaveRequest;
import com.fuar.stock.repository.mongo.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final StockEsService stockEsService;

    public Flux<StockResponse> getAll() {
        return stockEsService.findAll().map(this::mapToDto);
    }

    private StockResponse mapToDto(StockEs item) {
        if (item == null) {
            return null;
        }
        return StockResponse.builder()
                .id(item.getId())
                .build();
    }

    public StockResponse save(StockSaveRequest request) {
        Stock stock = Stock.builder()
                .id(request.getId())
                .build();
        stock = stockRepository.save(stock).block();

        // 3 - Redisten güncelle

        return this.mapToDto(stockEsService.saveNewStock(stock).block());
    }
}
