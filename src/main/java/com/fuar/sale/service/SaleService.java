package com.fuar.sale.service;

import com.fuar.sale.domain.Sale;
import com.fuar.sale.domain.es.SaleEs;
import com.fuar.sale.model.SaleResponse;
import com.fuar.sale.model.SaleSaveRequest;
import com.fuar.sale.repository.mongo.SaleRepository;
import com.fuar.stock.model.StockSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
        return SaleResponse.builder()
                .id(item.getId())
                .amount(item.getAmount())
                .build();
    }

    public SaleResponse save(SaleSaveRequest request) {
        Sale sale = Sale.builder()
                .id(request.getId())
                .amount(request.getAmount())
                .build();
        sale = saleRepository.save(sale).block();

        // 3 - Redisten güncelle

        return this.mapToDto(saleEsService.saveNewSale(sale).block());
    }
}
