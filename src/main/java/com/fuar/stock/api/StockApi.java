package com.fuar.stock.api;

import com.fuar.stock.model.StockResponse;
import com.fuar.stock.service.StockService;
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
    public Flux<StockResponse> getAllProducts() {
        return stockService.getAll();
    }
}
