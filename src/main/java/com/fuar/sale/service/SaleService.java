package com.fuar.sale.service;

import com.fuar.sale.domain.Sale;
import com.fuar.sale.domain.es.SaleEs;
import com.fuar.sale.model.SaleResponse;
import com.fuar.sale.model.SaleSaveRequest;
import com.fuar.sale.repository.mongo.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;
    private final SaleEsService saleEsService;

    public Flux<SaleResponse> getAll() {
        return saleEsService.findAll().map(this::mapToDto);
    }

    private SaleResponse mapToDto(SaleEs item) {
        if (item == null) {
            return null;
        }
        Locale locale = new Locale("tr", "TR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String date = dateFormat.format(item.getOrderDate());
        return SaleResponse.builder()
                .id(item.getId())
                .amount(item.getAmount())
                .moneyType(item.getMoney())
                .orderDate(date)
                .build();
    }

    public Mono saveMono(SaleSaveRequest request) {
        Sale sale = Sale.builder()
                .id(request.getId())
                .amount(request.getAmount())
                .money(request.getMoneyType())
                .orderDate(new Date())
                .build();
        Mono<Sale> monoSale = saleRepository.save(sale);
        //Mono<SaleEs> saleEsMono = saleEsService.saveNewSale(sale);

        return monoSale;
        //return null;
    }

    public SaleResponse save(SaleSaveRequest request) {
        Sale sale = Sale.builder()
                .id(request.getId())
                .amount(request.getAmount())
                .money(request.getMoneyType())
                .orderDate(request.getOrderDate())
                .build();
        saleRepository.save(sale);

        // 3 - Redisten güncelle
        return null;
        //return this.mapToDto(saleEsService.saveNewSale(sale).publishOn(Schedulers.elastic()).block());
    }


    public void delete(Long id) {
        Mono<Sale> sale = saleRepository.findById(id);
        if(sale != null) {
            Mono<Void> saleDelete = saleRepository.delete(Sale.builder().id(id).build());
            saleEsService.delete(id);

        }
    }
}
