package com.fuar.service.stock;

import com.fuar.domain.stock.Stock;
import com.fuar.domain.stock.es.StockEs;
import com.fuar.model.stock.StockResponseDto;
import com.fuar.model.stock.StockSaveRequestDto;
import com.fuar.repository.stock.StockRepository;
import com.fuar.service.stock.es.StockEsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final StockEsService stockEsService;

    public Flux<StockResponseDto> getAll() {
        return stockEsService.findAll().map(this::mapToDto);
    }

    private StockResponseDto mapToDto(StockEs item) {
        if (item == null) {
            return null;
        }
        return StockResponseDto.builder()
                .id(item.getId())
                .build();
    }

    public StockResponseDto save(StockSaveRequestDto request) {
        Stock stock = Stock.builder()
                .id(request.getId())
                .build();
        stock = stockRepository.save(stock).block();

        // 3 - Redisten güncelle

///        return this.mapToDto(stockEsService.saveNewStock(stock).block());
        return null;
    }
}
