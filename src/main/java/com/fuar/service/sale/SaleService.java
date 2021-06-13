package com.fuar.service.sale;

import com.fuar.domain.sale.Sale;
import com.fuar.model.sale.SaleResponseDto;
import com.fuar.model.sale.SaleSaveRequestDto;
import com.fuar.repository.sale.SaleRepository;
import com.fuar.service.sale.es.SaleEsService;
import com.fuar.service.sequence.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SaleService {
    Logger logger = LoggerFactory.getLogger(SaleService.class);

    private final SaleRepository saleRepository;
    private final SaleEsService saleEsService;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Transactional
    public Mono saveSale(SaleSaveRequestDto request) {
        String operationType = "CREATED";

        Sale sale = Sale.builder()
                .id(sequenceGeneratorService.generateSequence(Sale.SEQUENCE_NAME))
                .amount(request.getAmount())
                .money(request.getMoneyType())
                .orderDate(new Date())
                .operation(operationType)
                .build();

        Mono<Sale> monoSale = saleRepository.save(sale);
        saleEsService.saveNewSaleEs(sale)
                .subscribe(result -> logger.info("Entity has been saved to elastic search: {}", result));

        return monoSale;
    }

    @Transactional
    public Mono updateSale(SaleSaveRequestDto request) {
        Sale sale = saleRepository.findById(request.getId()).block();

        sale.setId(request.getId());
        sale.setAmount(request.getAmount());
        sale.setMoney(request.getMoneyType());
        sale.setOrderDate(request.getOrderDate());
        sale.setOperation("UPDATED");

        Mono<Sale> monoSale = saleRepository.save(sale);
        saleEsService.updateSaleEs(sale)
                .subscribe(result -> logger.info("Entity has been updated to elastic search: {}", result));

        return monoSale;
    }

    public SaleResponseDto save(SaleSaveRequestDto request) {
        Sale sale = Sale.builder()
              //  .id(request.getId())
                .amount(request.getAmount())
                .money(request.getMoneyType())
                .orderDate(request.getOrderDate())
                .build();
        saleRepository.save(sale);

        return null;
        //return this.mapToDto(saleEsService.saveNewSale(sale).publishOn(Schedulers.elastic()).block());
    }

    @Transactional
    public void delete(Long id) {
        Sale sale = saleRepository.findById(id).block();
        if(sale != null) {
            saleRepository.deleteById(id).subscribe(result -> logger.info("Entity has been deleted from elastic search: {}", result));
            saleEsService.delete(id);
        }
    }
}
