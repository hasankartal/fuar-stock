package com.fuar.service.stock;

import com.fuar.entity.stock.Stock;
import com.fuar.domain.stock.StockResponseDto;
import com.fuar.domain.stock.StockSaveRequestDto;
import com.fuar.repository.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    @Autowired
    private StockRepository stockRepository;
    //private final StockEsService stockEsService;

    public List<StockResponseDto> getAll() {
        List<Stock> saleList = stockRepository.findAll();
        List<StockResponseDto> stockResponseDtoList = new ArrayList<>();
        for (Stock stock : saleList) {
            StockResponseDto stockResponseDto = mapToDto(stock);
            stockResponseDtoList.add(stockResponseDto);
        }
        return stockResponseDtoList;
    }

    private StockResponseDto mapToDto(Stock item) {
        if (item == null) {
            return null;
        }
        return StockResponseDto.builder()
                .id(item.getId())
                .build();
    }

    public StockResponseDto save(StockSaveRequestDto request) {
        Stock stock = Stock.builder()
                .build();
        stock = stockRepository.save(stock);

        // 3 - Redisten güncelle

///        return this.mapToDto(stockEsService.saveNewStock(stock).block());
        return null;
    }
}
