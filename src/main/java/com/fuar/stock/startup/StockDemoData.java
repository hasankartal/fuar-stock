package com.fuar.stock.startup;

import com.fuar.stock.model.StockSaveRequest;
import com.fuar.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.UUID.randomUUID;

@Component
@RequiredArgsConstructor
public class StockDemoData {
    private final StockService stockService;

    @EventListener(ApplicationReadyEvent.class)
    public void migrate() {
        IntStream.range(0, 20).forEach(item -> {
            stockService.save(
                    StockSaveRequest.builder()
                            .id(new Long(3))
                            .build());
        });

    }
}
