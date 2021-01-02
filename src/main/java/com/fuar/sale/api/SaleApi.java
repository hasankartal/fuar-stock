package com.fuar.sale.api;

import com.fuar.sale.model.SaleResponse;
import com.fuar.sale.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SaleApi {
    private final SaleService saleService;

    @GetMapping
    public Flux<SaleResponse> getAllSales() {
        return saleService.getAll();
    }
}
