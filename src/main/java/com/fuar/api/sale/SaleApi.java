package com.fuar.api.sale;

import com.fuar.domain.sale.Sale;
import com.fuar.model.sale.SaleResponse;
import com.fuar.model.sale.SaleSaveRequest;
import com.fuar.service.sale.es.SaleEsService;
import com.fuar.service.sale.SaleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SaleApi {
    Logger logger = LoggerFactory.getLogger(SaleApi.class);
    private final SaleService saleService;
    private final SaleEsService saleEsService;

    @GetMapping
    public Flux<SaleResponse> getAllSales() {
        return saleService.getAll();
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono saveSale(@RequestBody SaleSaveRequest item) {
        Sale sale = Sale.builder()
                .amount(item.getAmount())
                .money(item.getMoneyType())
                .orderDate(new Date())
                .build();
        saleService.saveMono(item)
                .subscribe(result -> logger.info("Entity has been saved: {}", result));
//        saleEsService.saveNewSale(sale).subscribe(result -> logger.info("Entity has been saved: {}", result));
        return null;
    }

    @DeleteMapping("/delete")
//    @ResponseStatus(HttpStatus.CREATED)
    public void deleteSale(@RequestBody SaleSaveRequest item) {
        saleService.delete(item.getId());
    }

}
