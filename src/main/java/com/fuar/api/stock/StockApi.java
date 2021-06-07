package com.fuar.api.stock;

import com.fuar.model.stock.StockResponseDto;
import com.fuar.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StockApi {
    private final StockService stockService;

    @GetMapping
    public Flux<StockResponseDto> getAllProducts() {
        return stockService.getAll();
    }
}
