package com.fuar.service.sale;

import com.fuar.domain.sale.Sale;
import com.fuar.domain.sale.es.SaleEs;
import com.fuar.model.sale.SaleResponse;
import com.fuar.model.sale.SaleSaveRequest;
import com.fuar.repository.sale.SaleRepository;
import com.fuar.service.sale.es.SaleEsService;
import com.fuar.service.sequence.SequenceGeneratorService;
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
    private final SequenceGeneratorService sequenceGeneratorService;

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
                .id(sequenceGeneratorService.generateSequence(Sale.SEQUENCE_NAME))
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
              //  .id(request.getId())
                .amount(request.getAmount())
                .money(request.getMoneyType())
                .orderDate(request.getOrderDate())
                .build();
        saleRepository.save(sale);

        return null;
        //return this.mapToDto(saleEsService.saveNewSale(sale).publishOn(Schedulers.elastic()).block());
    }


    public void delete(Long id) {
        Mono<Sale> sale = saleRepository.findById(id);
        if(sale != null) {
            //Mono<Void> saleDelete = saleRepository.delete(Sale.builder().id(id).build());
            saleEsService.delete(id);

        }
    }
}
