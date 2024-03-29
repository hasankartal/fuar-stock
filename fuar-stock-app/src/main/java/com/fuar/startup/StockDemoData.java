package com.fuar.startup;

import com.fuar.common.MoneyType;
import com.fuar.domain.sale.SaleSaveRequestDto;
import com.fuar.service.sale.SaleService;
import com.fuar.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockDemoData {
    private final StockService stockService;
    private final SaleService saleService;

    @EventListener(ApplicationReadyEvent.class)
    public void migrate() {
       /* IntStream.range(0, 20).forEach(item -> {
            stockService.save(
                    StockSaveRequest.builder()
                            .id(new Random().nextLong())
                            .build());
        });
        IntStream.range(0, 5).forEach(item -> {
            saleService.save(
                    SaleSaveRequestDto.builder()
                           // .id(new Random().nextLong())
                            .amount(new Random().nextFloat())
                            .moneyType(MoneyType.TL.toString())
                            .orderDate(new Date())
                            .build());
        });
*/
    }
}
