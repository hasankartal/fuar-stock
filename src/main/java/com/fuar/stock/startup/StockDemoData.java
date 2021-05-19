package com.fuar.stock.startup;

import com.fuar.sale.model.SaleSaveRequest;
import com.fuar.sale.service.SaleService;
import com.fuar.stock.model.StockSaveRequest;
import com.fuar.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class StockDemoData {
    private final StockService stockService;
    private final SaleService saleService;

    @EventListener(ApplicationReadyEvent.class)
    public void migrate() {
        IntStream.range(0, 20).forEach(item -> {
            stockService.save(
                    StockSaveRequest.builder()
                            .id(new Long(3))
                            .build());
        });
        IntStream.range(0, 20).forEach(item -> {
            saleService.save(
                    SaleSaveRequest.builder()
                            .id(new Long(15))
                            .amount(125.50F)
                            .build());
        });

    }
}
