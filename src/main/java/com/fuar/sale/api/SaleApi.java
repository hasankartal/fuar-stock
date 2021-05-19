package com.fuar.sale.api;

import com.fuar.common.MoneyType;
import com.fuar.sale.domain.Sale;
import com.fuar.sale.domain.es.SaleEs;
import com.fuar.sale.model.SaleResponse;
import com.fuar.sale.model.SaleSaveRequest;
import com.fuar.sale.service.SaleEsService;
import com.fuar.sale.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

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
      /*  SaleResponse saleResponse =  saleService.save(
                SaleSaveRequest.builder()
                        .id(new Long(20))
                        .amount(258.50F)
                        .moneyType(MoneyType.TL.toString())
                        .orderDate(new Date())
                        .build());
*//*saleService.save(
                SaleSaveRequest.builder()
                        .id(24L)
                        .amount(221.50F)
                        .moneyType(MoneyType.TL.toString())
                        .orderDate(new Date())
                        .build());*/
        Sale sale = Sale.builder()
                .id(item.getId())
                .amount(item.getAmount())
                .money(item.getMoneyType())
                .orderDate(new Date())
                .build();
        saleService.saveMono(item)
                .subscribe(result -> logger.info("Entity has been saved: {}", result));
        //Mono<SaleEs> saleEsMono =;
        saleEsService.saveNewSale(sale)
                .subscribe(result -> logger.info("Entity has been saved: {}", result));

        //Mono<Void> all = Mono.when(monoSale, saleEsMono);
        //all.subscribeOn(Schedulers.fromExecutor(exec));

        return null;
        /*
       saleService.saveMono(
                SaleSaveRequest.builder()
                      //  .id(30L)
                        .amount(item.getAmount())
                        .moneyType(MoneyType.TL.toString())
                        .orderDate(new Date())
                        .build());
      *///  return null;
    }

    @DeleteMapping("/delete")
//    @ResponseStatus(HttpStatus.CREATED)
    public void deleteSale(@RequestBody SaleSaveRequest item) {
        saleService.delete(item.getId());
    }

}
